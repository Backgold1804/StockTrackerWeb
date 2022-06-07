package com.example.stocktracker.vo;

public class StockVO {
	String ticker;
	String insert_date;
	int open_price;
	int close_price;
	int high_price;
	int low_price;
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getInsert_date() {
		return insert_date;
	}
	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}
	public int getOpen_price() {
		return open_price;
	}
	public void setOpen_price(int open_price) {
		this.open_price = open_price;
	}
	public int getClose_price() {
		return close_price;
	}
	public void setClose_price(int close_price) {
		this.close_price = close_price;
	}
	public int getHigh_price() {
		return high_price;
	}
	public void setHigh_price(int high_price) {
		this.high_price = high_price;
	}
	public int getLow_price() {
		return low_price;
	}
	public void setLow_price(int low_price) {
		this.low_price = low_price;
	}
	
}
