package com.example.demo.entity;

public class Task {

	private int taskId;
	private String taskName;
	private double expectedManPower;
	private double actualManPower;
	private String startDate;
	private String endDate;
	private String userName;
	private double diffExAc;
	private int projectId;
	
	public Task() {}
	
	public Task(int taskId, String taskName, double expectedManPower, double actualManPower, String startDate,
			String endDate, String userName, int projectId) {
		this.taskId = taskId;
		this.taskName = taskName;
		this.expectedManPower = expectedManPower;
		this.actualManPower = actualManPower;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userName = userName;
		this.projectId = projectId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
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
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taskName=" + taskName + ", expectedManPower=" + expectedManPower
				+ ", actualManPower=" + actualManPower + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", userName=" + userName + ", projectId=" + projectId + "]";
	}

	public double getDiffExAc() {
		return diffExAc;
	}

	public void setDiffExAc(double diffExAc) {
		this.diffExAc = diffExAc;
	}
	
}
