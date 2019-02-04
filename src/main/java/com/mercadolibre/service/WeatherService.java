package com.mercadolibre.service;

import com.mercadolibre.model.Weather;

public interface WeatherService {
	public Weather getWeather (int day);
	public void create (Weather weather);
}
