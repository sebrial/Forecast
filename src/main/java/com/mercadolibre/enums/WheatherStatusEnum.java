package com.mercadolibre.enums;

public enum WheatherStatusEnum {
	DROUGHT("SEQUÍA"), OPTIMAL("OPTIMO"), RAIN("LLUVIA"), INDETERMINATE("INDEFINIDO");

	private String value;

	private WheatherStatusEnum(String description) {
		this.value = description;
	}

	@Override
	public String toString() {
		return value;
	}
}
