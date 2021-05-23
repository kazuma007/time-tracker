package com.example.demo.entity;

public class Project {

	private int projectId;
	private String projectName;
	private String content;
	private String projectCode;
	private String date;
	public Project() {}
	
	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", projectName=" + projectName + ", content=" + content
				+ ", projectCode=" + projectCode + ", date=" + date + "]";
	}

	public Project(int projectId, String projectName, String projectCode, String content, String date) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.content = content;
		this.setProjectCode(projectCode);
		this.date = date;
	}

	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getdate() {
		return date;
	}
	public void setdate(String date) {
		this.date = date;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
}
