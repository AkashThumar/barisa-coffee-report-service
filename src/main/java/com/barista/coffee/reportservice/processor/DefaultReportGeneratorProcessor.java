package com.barista.coffee.reportservice.processor;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.barista.coffee.reportservice.ReportServiceException;
import com.barista.coffee.reportservice.bean.ErrorBean;
import com.barista.coffee.reportservice.repository.OrderRepository;

@Component
public class DefaultReportGeneratorProcessor implements ReportGenerator {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public void registerReportGenerator(ReportGeneratorRegistry reportGeneratorRegistry) {
		reportGeneratorRegistry.registerReportGenerator(ReportGeneratorEnum.Default, this);
	}

	@Override
	public byte[] generateOrderReport() {
		// fetch order records for today
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		Long totalQuantitySoldToday = orderRepository.numberOfCoffeesSoldToday(calendar.getTime(), "COMPLETED");
		// create a workbook with default # of coffees sold today and return byte array
		return this.generateReport(calendar, totalQuantitySoldToday);
	}

	private byte[] generateReport(Calendar calendar, Long totalQuantitySoldToday) {
		Workbook workbook = new XSSFWorkbook();

		Sheet sheet = workbook.createSheet("#coffees sold");
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 5000);

		Row row = sheet.createRow(0);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		Cell cell = row.createCell(0);
		cell.setCellValue("Date");
		cell.setCellStyle(headerStyle);

		cell = row.createCell(1);
		cell.setCellValue("Total Coffees Sold");
		cell.setCellStyle(headerStyle);

		row = sheet.createRow(1);

		cell = row.createCell(0);
		cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));

		cell = row.createCell(1);
		cell.setCellValue(totalQuantitySoldToday);

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
			workbook.write(outputStream);
			workbook.close();
			return outputStream.toByteArray();
		} catch (FileNotFoundException e) {
			throw new ReportServiceException(HttpStatus.INTERNAL_SERVER_ERROR,
					new ErrorBean("BCRS-501", "Internal Server Error"), e);
		} catch (IOException e) {
			throw new ReportServiceException(HttpStatus.INTERNAL_SERVER_ERROR,
					new ErrorBean("BCRS-501", "Internal Server Error"), e);
		}
	}

}
