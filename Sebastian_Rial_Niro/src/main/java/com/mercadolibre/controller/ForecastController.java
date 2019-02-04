package com.mercadolibre.controller;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.dto.WeatherDto;
import com.mercadolibre.model.Weather;
import com.mercadolibre.service.WeatherService;

@RestController
public class ForecastController {
	@Autowired
	private WeatherService weatherService;
	
	@RequestMapping(value = "/weather/day={day}", method = RequestMethod.GET)
	@ResponseBody
	public WeatherDto getWeather(@PathVariable("day") int day) {

		Weather weather = weatherService.getWeather(day);

		Mapper mapper = new DozerBeanMapper();
		WeatherDto weatherDto = mapper.map(weather, WeatherDto.class);

		return weatherDto;

	}
}
