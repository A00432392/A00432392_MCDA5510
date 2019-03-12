package com.mcda5510.entity;
import java.util.*;
import java.sql.*;
import java.sql.Date;

public class Transaction{
	private int ID;

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	
	private String nameOnCard;
	
	private String cardNumber;
	
	private float unitPrice;
	
	private int quantity;
	
	private float totalPrice;
	
	private String expDate;
	
	private Date createdOn;
	
	private String createdBy;
	
	
	
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getNameOnCard() {
		return nameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}	
	
	public String toString(){
		
		String results = new String();
		
		results = "[NameOnCard: " + nameOnCard +",CardNumber: " + cardNumber+",CreatedOn:"+createdOn+"]";
		return results;

	}
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	
}
