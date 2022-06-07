package com.example.stocktracker.vo;

public class ChartListVO {
	String stock_name;
	String close_price;
	String holdings;
	
	public String getStock_name() {
		return stock_name;
	}
	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}
	public String getClose_price() {
		return close_price;
	}
	public void setClose_price(String close_price) {
		this.close_price = close_price;
	}
	public String getHoldings() {
		return holdings;
	}
	public void setHoldings(String holdings) {
		this.holdings = holdings;
	}
}
