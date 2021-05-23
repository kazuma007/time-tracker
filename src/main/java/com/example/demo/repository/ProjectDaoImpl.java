package com.example.demo.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Project;
import com.example.demo.entity.Task;

@Repository
public class ProjectDaoImpl implements ProjectDao {
	
	private final JdbcTemplate jt;
	private final String registerSql = 
			"insert into project(projectname, content, projectCode, date) values(?,?,?,?)";
	private final String getOneSql = 
			"select * from project where projectCode = ?";

	@Autowired
	public ProjectDaoImpl(JdbcTemplate jt) {
		this.jt = jt;
	}

	@Override
	public void registerProject(Project project) {
		Date dateTemp = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(dateTemp);
		jt.update(registerSql, project.getProjectName(),
			project.getContent(), project.getProjectCode(), date);
	}

	@Override
	public Project getProject(String projectCode) {
		Map<String, Object> oneProject = null;
		try {
			oneProject = 
					jt.queryForMap(getOneSql, projectCode);
		}catch(IncorrectResultSizeDataAccessException e) {
			return null;
		}

		Project project = new Project();
		project.setProjectId((Integer) oneProject.get("projectId"));
		project.setProjectName((String) oneProject.get("projectName")); 
		project.setContent((String) oneProject.get("content")); 
		project.setdate((String) oneProject.get("date"));
		return project;
	}


}
