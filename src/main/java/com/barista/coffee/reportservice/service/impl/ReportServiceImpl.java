package com.barista.coffee.reportservice.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.barista.coffee.reportservice.ReportServiceException;
import com.barista.coffee.reportservice.bean.ErrorBean;
import com.barista.coffee.reportservice.processor.ReportGenerator;
import com.barista.coffee.reportservice.processor.ReportGeneratorEnum;
import com.barista.coffee.reportservice.processor.ReportGeneratorRegistry;
import com.barista.coffee.reportservice.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	private ReportGeneratorRegistry reportGeneratorRegistry = new ReportGeneratorRegistry();

	@Autowired
	public ReportServiceImpl(ListableBeanFactory beanFactory) {
		Collection<ReportGenerator> processors = beanFactory.getBeansOfType(ReportGenerator.class).values();
		processors.forEach(processor -> {
			processor.registerReportGenerator(reportGeneratorRegistry);
		});
	}

	@Override
	public byte[] generateReport(ReportGeneratorEnum reportGeneratorEnum) {
		ReportGenerator reportGenerator = reportGeneratorRegistry.getReportGeneratorMap().get(reportGeneratorEnum);
		if (null != reportGenerator) {
			return reportGenerator.generateOrderReport();
		} else {
			throw new ReportServiceException(HttpStatus.NOT_IMPLEMENTED,
					new ErrorBean("BCRS-110", "Report processor not defined for the requeted filter"), null);
		}
	}
}