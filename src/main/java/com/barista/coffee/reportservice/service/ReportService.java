package com.barista.coffee.reportservice.service;

import com.barista.coffee.reportservice.processor.ReportGeneratorEnum;

public interface ReportService {

	public byte[] generateReport(ReportGeneratorEnum reportGeneratorEnum);

}
