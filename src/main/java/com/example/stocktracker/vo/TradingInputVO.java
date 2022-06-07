package com.example.stocktracker.vo;

public class TradingInputVO {
	int uid;
	int my_stock_uid;
	String ticker;
	String trading;
	int order_price;
	int order_amount;
	long order_sum;
	String insert_date;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getMy_stock_uid() {
		return my_stock_uid;
	}
	public void setMy_stock_uid(int my_stock_uid) {
		this.my_stock_uid = my_stock_uid;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getTrading() {
		return trading;
	}
	public void setTrading(String trading) {
		this.trading = trading;
	}
	public int getOrder_price() {
		return order_price;
	}
	public void setOrder_price(int order_price) {
		this.order_price = order_price;
	}
	public int getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(int order_amount) {
		this.order_amount = order_amount;
	}
	public long getOrder_sum() {
		return order_sum;
	}
	public void setOrder_sum(long order_sum) {
		this.order_sum = order_sum;
	}
	public String getInsert_date() {
		return insert_date;
	}
	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}
	
	
}
