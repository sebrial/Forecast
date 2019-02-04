package com.mercadolibre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercadolibre.dao.WeatherDao;
import com.mercadolibre.model.Weather;
import com.mercadolibre.service.WeatherService;

@Service("weatherService")
public class WeatherServiceImpl implements WeatherService{
	@Autowired
	private WeatherDao weatherDao;
	
	@Override
	public Weather getWeather(int day) {
		return weatherDao.findByDay(day);
	}
	@Transactional
	@Override
	public void create(Weather weather) {
		weatherDao.create(weather);
		
	}

}
