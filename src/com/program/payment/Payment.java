package com.program.payment;

import java.sql.Timestamp;

public class Payment {
	private int ID;
	private String userName;
	private int checkEXP;
	private int cleanEXP;
	private int parkEXP;
	private Timestamp payDate;
	private String isPay;
	
	public Payment() {
		
	}
	
	public Payment(String userName, int checkEXP, int cleanEXP, int parkEXP, Timestamp payDate, String isPay) {
		super();
		this.userName = userName;
		this.checkEXP = checkEXP;
		this.cleanEXP = cleanEXP;
		this.parkEXP = parkEXP;
		this.payDate = payDate;
		this.isPay = isPay;
	}
	
	

	public Payment(int ID, String userName, int checkEXP, int cleanEXP, int parkEXP, Timestamp payDate, String isPay) {
		this(userName,checkEXP,cleanEXP,parkEXP,payDate,isPay);
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getCheckEXP() {
		return checkEXP;
	}

	public void setCheckEXP(int checkEXP) {
		this.checkEXP = checkEXP;
	}

	public int getCleanEXP() {
		return cleanEXP;
	}

	public void setCleanEXP(int cleanEXP) {
		this.cleanEXP = cleanEXP;
	}

	public int getParkEXP() {
		return parkEXP;
	}

	public void setParkEXP(int parkEXP) {
		this.parkEXP = parkEXP;
	}

	public Timestamp getPayDate() {
		return payDate;
	}

	public void setPayDate(Timestamp payDate) {
		this.payDate = payDate;
	}

	public String getIsPay() {
		return isPay;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}

	
	@Override
	public String toString() {
		return "Payment [ID=" + ID + ", userName=" + userName + ", checkEXP=" + checkEXP + ", cleanEXP=" + cleanEXP
				+ ", parkEXP=" + parkEXP + ", payDate=" + payDate + ", isPay=" + isPay + "]";
	}

	public Object[] toStrArray() {
		Object[] objects=new Object[] {ID,userName,checkEXP,cleanEXP,parkEXP,payDate,isPay};
		return objects;
	}
	
}
