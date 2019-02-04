package com.mercadolibre.dto;

public class WeatherDto {
	private int day;
	private String status;
	
	public WeatherDto () {}
	
	public WeatherDto (int day, String status) {
		this.day = day;
		this.status = status;
				
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
