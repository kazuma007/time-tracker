package com.example.demo.repository;

import com.example.demo.entity.Project;
import com.example.demo.entity.Task;

public interface ProjectDao {
	void registerProject(Project project);
	Project getProject(String projectCode);
}
