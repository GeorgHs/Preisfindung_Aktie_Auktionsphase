package com.georghs.finishedapi.entity;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

public class RunningBook {
	private Stock stock;
	private LinkedHashMap<LocalDateTime, Double> lastPrices;
	public RunningBook(Stock stock, LinkedHashMap<LocalDateTime, Double> lastPrices) {
		super();
		this.stock = stock;
		this.lastPrices = lastPrices;
	}
	
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public LinkedHashMap<LocalDateTime, Double> getLastPrices() {
		return lastPrices;
	}
	public void setLastPrices(LinkedHashMap<LocalDateTime, Double> lastPrices) {
		this.lastPrices = lastPrices;
	}
	
	public LastPrice getLastPrice() {
		return LastPrice.newLastPrice(lastPrices);
	}
	
	public void setLastPrice() {
		
	}
}

class LastPrice {
	private LocalDateTime timeStamp;
	private Double price;
	
	public LastPrice(LocalDateTime timeStamp, Double price) {
		super();
		this.timeStamp = timeStamp;
		this.price = price;
	}
	
	public static LastPrice newLastPrice(LinkedHashMap<LocalDateTime, Double> lastPrices) {
		return new LastPrice((LocalDateTime)lastPrices.keySet().toArray()[0], (Double)lastPrices.values().toArray()[0]);
	}
	
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}