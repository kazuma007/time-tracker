package com.example.demo.app;

public class GetProjectForm {
	private String projectCode;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public GetProjectForm(String projectCode) {
		this.projectCode = projectCode;
	}

	public GetProjectForm() {
	}
}
