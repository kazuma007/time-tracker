package com.example.demo.app;

public class CheckTaskForm {
	private String userName;
	private String startDate;
	private String endDate;
	public CheckTaskForm() {
	}
	public CheckTaskForm(String userName, String startDate, String endDate) {
		this.userName = userName;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "CheckTaskForm [userName=" + userName + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
