package com.arangodb.spark.java;

import java.io.Serializable;

public class TestJavaEntity implements Serializable {
	
	private static final long serialVersionUID = 2886378075044424343L;
	
	private Boolean booleanValue = true;
	private Double doubleValue = Double.MAX_VALUE;
	private Float floatValue = Float.MAX_VALUE;
	private Long longValue = Long.MAX_VALUE;
	private Integer intValue = Integer.MAX_VALUE;
	private Short shortValue = Short.MAX_VALUE;
	private String stringValue = "test";

	public TestJavaEntity() {
		super();
	}

	public Boolean getBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public Double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public Float getFloatValue() {
		return floatValue;
	}

	public void setFloatValue(Float floatValue) {
		this.floatValue = floatValue;
	}

	public Long getLongValue() {
		return longValue;
	}

	public void setLongValue(Long longValue) {
		this.longValue = longValue;
	}

	public Integer getIntValue() {
		return intValue;
	}

	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}

	public Short getShortValue() {
		return shortValue;
	}

	public void setShortValue(Short shortValue) {
		this.shortValue = shortValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

}
