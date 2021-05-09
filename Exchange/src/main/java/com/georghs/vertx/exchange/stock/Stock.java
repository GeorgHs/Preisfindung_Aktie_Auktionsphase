package com.georghs.vertx.exchange.stock;

public class Stock {
	double price;
	String description; 
	
	public Stock(double number, String description) {
		super();
		this.price = number;
		this.description = description;
	}
	public double getNumber() {
		return price;
	}
	public void setNumber(double number) {
		this.price = number;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
