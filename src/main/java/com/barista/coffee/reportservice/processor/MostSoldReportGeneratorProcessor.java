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
public class MostSoldReportGeneratorProcessor implements ReportGenerator {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public void registerReportGenerator(ReportGeneratorRegistry reportGeneratorRegistry) {
		reportGeneratorRegistry.registerReportGenerator(ReportGeneratorEnum.MostSold, this);
	}

	@Override
	public byte[] generateOrderReport() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		Object[] result = (Object[]) orderRepository.mostSoldCoffeeOfTheDay(calendar.getTime(), "COMPLETED");
		
		return this.generateReport(calendar, result);
	}

	private byte[] generateReport(Calendar calendar, Object[] result) {
		Workbook workbook = new XSSFWorkbook();

		Sheet sheet = workbook.createSheet("Most Sold");
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);

		Row row = sheet.createRow(0);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		Cell cell = row.createCell(0);
		cell.setCellValue("Date");
		cell.setCellStyle(headerStyle);

		cell = row.createCell(1);
		cell.setCellValue("Coffee Name");
		cell.setCellStyle(headerStyle);
		
		cell = row.createCell(2);
		cell.setCellValue("Sold quantity");
		cell.setCellStyle(headerStyle);

		row = sheet.createRow(1);

		cell = row.createCell(0);
		cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));

		cell = row.createCell(1);
		cell.setCellValue(null != result[0] ? result[0].toString().trim() : "");
		
		cell = row.createCell(2);
		cell.setCellValue(null != result[1] ? result[1].toString().trim() : null);

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
			workbook.write(outputStream);
			workbook.close();
			return outputStream.toByteArray();
		} catch (FileNotFoundException e) {
			throw new ReportServiceException(HttpStatus.INTERNAL_SERVER_ERROR,
					new ErrorBean("BCRS-502", "Internal Server Error"), e);
		} catch (IOException e) {
			throw new ReportServiceException(HttpStatus.INTERNAL_SERVER_ERROR,
					new ErrorBean("BCRS-502", "Internal Server Error"), e);
		}
	}

}
