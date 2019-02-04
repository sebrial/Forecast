package com.mercadolibre.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mercadolibre.dao.WeatherDao;
import com.mercadolibre.model.Weather;

@Repository("weatherDao")
public class WeatherDaoImpl implements WeatherDao{
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public void create(Weather weather) {
		entityManager.merge(weather);
		
	}

	@Override
	public Weather findByDay(int day) {		
		return entityManager.find(Weather.class, day);
		
	}

}
