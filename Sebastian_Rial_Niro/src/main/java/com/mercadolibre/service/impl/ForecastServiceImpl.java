package com.mercadolibre.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.enums.WheatherStatusEnum;
import com.mercadolibre.model.Planet;
import com.mercadolibre.model.Weather;
import com.mercadolibre.service.ForecastService;
import com.mercadolibre.service.WeatherService;

@Service("forecastService")
public class ForecastServiceImpl implements ForecastService{ 
	private final static int DAYS_FOR_YEAR = 365;
	private final static Logger logger = Logger.getLogger(ForecastServiceImpl.class.getName());
	private Planet ferengi;
	private Planet betasoide;
	private Planet vulcano;
	private double maxPerimeter;
	private int forecatsDay;
	private int periodsOfDrought;
	private int periodsOfRain;
	private int dayOfMaximumIntensityOfRain;
	private int periodsOfOptimalConditions;
	
	@Autowired
	WeatherService weatherService;
	
	public List<Weather> generateForecast(int periodYears) {
		init();
		List<Weather> listOfWeather = new ArrayList<>();
		int days = periodYears * DAYS_FOR_YEAR;
		Weather auxWeather;
		for(int i=0; i < days ;i++) {
			auxWeather = predictWeatherForNextDay();
			if (auxWeather != null) {
				weatherService.create(auxWeather);
				listOfWeather.add(auxWeather);
			}
		}
		
		logger.log(Level.INFO, "Periods of drought: " + periodsOfDrought);
		logger.log(Level.INFO, "Periods of rain: " + periodsOfRain);
		logger.log(Level.INFO, "Periods of optimal conditions: " + periodsOfOptimalConditions);
		logger.log(Level.INFO, "Day of maximum intensity of rain: " + dayOfMaximumIntensityOfRain);
		
		return listOfWeather;
	}
	
	private void init() {
		forecatsDay = 0;
		periodsOfDrought = 0;
		periodsOfRain = 0;
		maxPerimeter = 0d;
		periodsOfOptimalConditions = 0;
		dayOfMaximumIntensityOfRain = 0;
		ferengi =  new Planet(90d,500d,-1d);
		betasoide =  new Planet(90d,2000d,-3d);
		vulcano =  new Planet(90d,1000d,5d);
		
	}
	
	private Weather predictWeatherForNextDay (){
		
		forecatsDay = forecatsDay + 1;
		ferengi.move();
		betasoide.move();
		vulcano.move();
		
		if(theyAreAlignedWithTheSun()) {
			periodsOfDrought = periodsOfDrought + 1;
			return new Weather(forecatsDay,WheatherStatusEnum.DROUGHT.toString());
		} else if (theyAreAlignedWithoutTheSun()) {
			//Ecuacion de recta que pase poe tres puntos
			periodsOfOptimalConditions = periodsOfOptimalConditions + 1;
			return new Weather(forecatsDay,WheatherStatusEnum.OPTIMAL.toString());
		} else if (theyFormATriangleWithTheSun()) {
			periodsOfRain = periodsOfRain + 1;
			Double perimeter = calculatePerimeter();
			if (maxPerimeter < perimeter) {
				maxPerimeter = perimeter;
				dayOfMaximumIntensityOfRain = forecatsDay;
			}
			return new Weather(forecatsDay,WheatherStatusEnum.RAIN.toString());
		} else {
		//TrianguloNoContieneSol
			return new Weather(forecatsDay,WheatherStatusEnum.INDETERMINATE.toString());
		}
		
	}

	private boolean theyAreAlignedWithTheSun() {
		//tomo la posicion de un planeta cualquiera e invierto su posicion para obtener 
		//las dos posibles posiciones para estar alineado al sol
		double currentAngle = ferengi.getAngle();
		double oppositeAngle;
		if (ferengi.getAngle() <= 180) {
			oppositeAngle = ferengi.getAngle() + 180;
		} else {
			oppositeAngle = ferengi.getAngle() - 180;
		}
		if ( betasoide.getAngle() == currentAngle ||
	    	 betasoide.getAngle() == oppositeAngle &&
	    	 vulcano.getAngle() == currentAngle ||
	    	 vulcano.getAngle() == oppositeAngle) {

			return true;
		} else {

			return false;
		}

	}

	private boolean theyAreAlignedWithoutTheSun() {
		//Y-Y0 = m(X-X0)

		// m = Y2-Y1
		//     -----
		//     X2-X1

		// para hallar la pendiente de la recta tomo dos puntos (planetas) al azar
		double pendiente = ( betasoide.getY() - ferengi.getY() ) / ( betasoide.getX() - ferengi.getX() );
		
		// para la formula de la recta tomo como punto inicial al planeta vulcano
		//Y-Y0 = m (X-X0)
		if ( vulcano.getY() == pendiente * ( vulcano.getX() - ferengi.getX() ) + ferengi.getY() ) {
			return true;
		} else {
			return false;
		}
	}

	private boolean theyFormATriangleWithTheSun() {
		//calculo el area del triangulo que se forma entre los tres planetas y la comparo con 
		//la sumatoria de las areas de todos los triangulos que se forman entre los 3 planetas 
		//y el sol si las areas son iguales el sol esta contenido de no ser igual no esta contenido
		
		double totalArea = calculateArea(ferengi.getX(), ferengi.getY(), betasoide.getX(), betasoide.getY(), vulcano.getX(), vulcano.getY());
		double area1 = calculateArea(0d, 0d, betasoide.getX(), betasoide.getY(), vulcano.getX(), vulcano.getY());
		double area2 = calculateArea(ferengi.getX(), ferengi.getY(), 0d, 0d, vulcano.getX(), vulcano.getY());
		double area3 = calculateArea(ferengi.getX(), ferengi.getY(), betasoide.getX(), betasoide.getY(), 0d, 0d);
		
		return totalArea == area1 + area2 + area3;
		
	}
	
	private double calculateArea(Double x1, Double y1, Double x2, Double y2, Double x3, Double y3) { 
		return Math.abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2))/2.0); 
	}
	
	private double calculatePerimeter() {
		return calculateDistance(ferengi.getX(),ferengi.getY(),betasoide.getX(),betasoide.getY()) + 
				calculateDistance(betasoide.getX(),betasoide.getY(),vulcano.getX(),vulcano.getY()) + 
				calculateDistance(vulcano.getX(),vulcano.getY(),ferengi.getX(),ferengi.getY());
		
	}
	private double calculateDistance(Double x1, Double y1, Double x2, Double y2) {
		return Math.sqrt( Math.pow( x2 - x1, 2) + Math.pow( y2 - y1 , 2) );
	}
	
	
	
}
