package com.barista.coffee.reportservice.processor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ReportGeneratorRegistry {

	private Map<ReportGeneratorEnum, ReportGenerator> reportGeneratorMap;
	
	private ReportGeneratorRegistry() {
		this.reportGeneratorMap = new HashMap<>();
	}
	
	public void registerReportGenerator(ReportGeneratorEnum reportGeneratorEnum, ReportGenerator generatorClass) {
		this.reportGeneratorMap.put(reportGeneratorEnum, generatorClass);
	}
	
	public Map<ReportGeneratorEnum, ReportGenerator> getReportGeneratorMap() {
		return this.reportGeneratorMap;
	}
	
}
