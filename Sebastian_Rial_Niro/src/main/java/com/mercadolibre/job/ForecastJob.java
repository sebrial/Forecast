package com.mercadolibre.job;

import org.springframework.beans.factory.annotation.Autowired;

import com.mercadolibre.service.ForecastService;

public class ForecastJob {
	@Autowired
	private ForecastService forecastService;

	public void execute() {
		forecastService.generateForecast(10);
	}
}
