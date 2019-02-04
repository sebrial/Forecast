package com.mercadolibre.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WEATHER")
public class Weather {
	@Id
	@Column(name = "DAY")
	private int day;
	@Column(name = "STATUS")
	private String status;
	
	public Weather () {}
	
	public Weather (int day, String status) {
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
