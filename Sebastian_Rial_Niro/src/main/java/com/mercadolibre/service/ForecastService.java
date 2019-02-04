package com.mercadolibre.service;

import java.util.List;

import com.mercadolibre.model.Weather;

public interface ForecastService {
	
	public List<Weather> generateForecast(int periodYears);

}
