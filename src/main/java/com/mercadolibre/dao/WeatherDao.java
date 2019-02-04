package com.mercadolibre.dao;

import com.mercadolibre.model.Weather;

public interface WeatherDao {
	public void create(Weather weather);

	public Weather findByDay(int day);
}
