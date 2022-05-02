package com.barista.coffee.reportservice.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.barista.coffee.reportservice.repository.OrderRepository;

@SpringBootTest
public class DefaultReportGeneratorProcessorTest {

	@InjectMocks
	DefaultReportGeneratorProcessor defaultReportGeneration;

//	@Autowired
	OrderRepository orderRepository;

//	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

//	@Test
	public void testRepo() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		// create a workbook with default # of coffees sold today
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

		File file = new File(".");
		String path = file.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";
		try {
			FileOutputStream outStream2 = new FileOutputStream(fileLocation);
			workbook.write(outStream2);
			
			workbook.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
