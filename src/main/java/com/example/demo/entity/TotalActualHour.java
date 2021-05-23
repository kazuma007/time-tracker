package com.example.demo.entity;

public class TotalActualHour {
	private String userName;
	private double totalActualManPower;
	
	public TotalActualHour() {}

	public TotalActualHour(String userName, double totalActualManPower) {
		this.userName = userName;
		this.totalActualManPower = totalActualManPower;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getTotalActualManPower() {
		return totalActualManPower;
	}

	public void setTotalActualManPower(double totalActualManPower) {
		this.totalActualManPower = totalActualManPower;
	}

	@Override
	public String toString() {
		return "TotalActualHour [userName=" + userName + ", totalActualManPower=" + totalActualManPower + "]";
	}
	

}
