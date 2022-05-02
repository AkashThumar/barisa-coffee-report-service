package com.barista.coffee.reportservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Report Generated Successfully"),
			@ApiResponse(code = 400, message = "Bad request parameters"),
			@ApiResponse(code = 422, message = "Unprocessable request", response = ErrorBean.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorBean.class) })
	@PostMapping(value = "/v1/reports")
	public ResponseEntity<InputStreamResource> generateReport(
			@RequestParam(name = "filterBy", required = true) ReportGeneratorEnum filterBy) {
		return null;
	}
}
