package com.example.demo.service;

import com.example.demo.entity.Project;
import com.example.demo.entity.Task;

public interface ProjectService {
	void registerProject(Project project);
	Project getOneProject(String projectCode);
}
