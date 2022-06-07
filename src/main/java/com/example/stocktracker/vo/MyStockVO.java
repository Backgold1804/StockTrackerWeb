package com.example.stocktracker.vo;

public class MyStockVO {
	int uid;
	int cust_uid;
	String ticker;
	int blended_price;
	int holdings;
	int buy_amount;
	String insert_date;
	String update_date;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getCust_uid() {
		return cust_uid;
	}
	public void setCust_uid(int cust_uid) {
		this.cust_uid = cust_uid;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public int getBlended_price() {
		return blended_price;
	}
	public void setBlended_price(int blended_price) {
		this.blended_price = blended_price;
	}
	public int getHoldings() {
		return holdings;
	}
	public void setHoldings(int holdings) {
		this.holdings = holdings;
	}
	
	public int getBuy_amount() {
		return buy_amount;
	}
	public void setBuy_amount(int buy_amount) {
		this.buy_amount = buy_amount;
	}
	public String getInsert_date() {
		return insert_date;
	}
	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	
}
