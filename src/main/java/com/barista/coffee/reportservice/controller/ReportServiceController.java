package com.barista.coffee.reportservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.barista.coffee.reportservice.bean.ErrorBean;
import com.barista.coffee.reportservice.processor.ReportGeneratorEnum;
import com.barista.coffee.reportservice.service.ReportService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class ReportServiceController {

	@Autowired
	private ReportService reportService;

	@ApiOperation(value = "Report Generation API", notes = "Report Generation API", httpMethod = "POST")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Report Generated Successfully"),
			@ApiResponse(code = 400, message = "Bad request parameters"),
			@ApiResponse(code = 422, message = "Unprocessable request", response = ErrorBean.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorBean.class) })
	@PostMapping(value = "/v1/reports", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<ByteArrayResource> generateReport(
			@RequestParam(name = "filterBy", required = true) ReportGeneratorEnum filterBy) {
		ByteArrayResource report = new ByteArrayResource(reportService.generateReport(filterBy));
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx");
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
		return new ResponseEntity<>(report, headers, HttpStatus.OK);
	}
}