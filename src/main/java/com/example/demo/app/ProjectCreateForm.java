package com.example.demo.app;

public class ProjectCreateForm {

	private String projectName;
	private String content;
	private String projectCode;
	public ProjectCreateForm() {}
	
	public ProjectCreateForm(String projectName, String content, String projectCode) {
		this.projectName = projectName;
		this.content = content;
		this.projectCode = projectCode;
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

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
}
