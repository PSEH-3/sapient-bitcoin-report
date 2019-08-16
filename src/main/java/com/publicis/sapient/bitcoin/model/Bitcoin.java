package com.publicis.sapient.bitcoin.model;

import java.time.LocalDate;

public class Bitcoin {
	private LocalDate date;
	private String price;
	private String currency;
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
