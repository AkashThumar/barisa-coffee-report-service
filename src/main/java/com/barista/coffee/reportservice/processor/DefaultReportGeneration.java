package com.barista.coffee.reportservice.processor;

import org.springframework.stereotype.Component;

@Component
public class DefaultReportGeneration implements ReportGenerator {

	@Override
	public void registerReportGenerator(ReportGeneratorRegistry reportGeneratorRegistry) {
		reportGeneratorRegistry.registerReportGenerator(ReportGeneratorEnum.Default, this);
	}

	@Override
	public byte[] generateOrderReport() {
		// TODO Auto-generated method stub
		return null;
	}

}
