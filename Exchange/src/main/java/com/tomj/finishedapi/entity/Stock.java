package com.tomj.finishedapi.entity;

import java.time.LocalDateTime;

public class Stock {

	private String id;
	private Double currentPrice;
	private LocalDateTime timeOfPriceSet;
	public Stock(String id, Double currentPrice, LocalDateTime timeOfPriceSet) {
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
	public LocalDateTime getTimeOfPriceSet() {
		return timeOfPriceSet;
	}
	public void setTimeOfPriceSet(LocalDateTime timeOfPriceSet) {
		this.timeOfPriceSet = timeOfPriceSet;
	}
	
	
}
