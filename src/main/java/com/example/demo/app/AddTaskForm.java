package com.example.demo.app;

public class AddTaskForm {

	private String taskName;
	private double expectedManPower;
	private double actualManPower;
	private String startDate;
	private String endDate;
	private String userName;
	
	public AddTaskForm() {}
	
	public AddTaskForm(String taskName, double expectedManPower, double actualManPower, String startDate,
			String endDate, String userName) {
		this.taskName = taskName;
		this.expectedManPower = expectedManPower;
		this.actualManPower = actualManPower;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userName = userName;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public double getExpectedManPower() {
		return expectedManPower;
	}
	public void setExpectedManPower(double expectedManPower) {
		this.expectedManPower = expectedManPower;
	}
	public double getActualManPower() {
		return actualManPower;
	}
	public void setActualManPower(double actualManPower) {
		this.actualManPower = actualManPower;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "AddTaskForm [taskName=" + taskName + ", expectedManPower=" + expectedManPower + ", actualManPower="
				+ actualManPower + ", startDate=" + startDate + ", endDate=" + endDate + ", userName=" + userName + "]";
	}
}
