package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Project;
import com.example.demo.entity.Task;
import com.example.demo.repository.ProjectDao;

@Service
public class ProjectServiceImpl implements ProjectService {

	private final ProjectDao pd;
	
	@Autowired
	public ProjectServiceImpl(ProjectDao pd) {
		this.pd = pd;
	}
	
	@Override
	public void registerProject(Project project) {
		pd.registerProject(project);
	}

	@Override
	public Project getOneProject(String projectCode) {
		Project project = pd.getProject(projectCode);
		return project;
	}

}
