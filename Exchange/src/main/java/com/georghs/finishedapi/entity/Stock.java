package com.georghs.finishedapi.entity;

import java.time.LocalDateTime;

public class Stock {

	private String id;
	private Double currentPrice;
	private String timeOfPriceSet;
	public Stock(String id, Double currentPrice, String timeOfPriceSet) {
		super();
		this.id = id;
		this.currentPrice = currentPrice;
		this.timeOfPriceSet = timeOfPriceSet;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public String getTimeOfPriceSet() {
		return timeOfPriceSet;
	}
	public void setTimeOfPriceSet(String timeOfPriceSet) {
		this.timeOfPriceSet = timeOfPriceSet;
	}
	
	
}
