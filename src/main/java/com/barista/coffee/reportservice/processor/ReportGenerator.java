package com.barista.coffee.reportservice.processor;

public interface ReportGenerator {

	public void registerReportGenerator(ReportGeneratorRegistry reportGeneratorRegistry);
	
	public byte[] generateOrderReport();
	
}
