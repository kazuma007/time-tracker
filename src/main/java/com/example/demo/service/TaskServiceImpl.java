package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Task;
import com.example.demo.entity.TotalActualHour;
import com.example.demo.repository.TaskDao;

@Service
public class TaskServiceImpl implements TaskService {
	
	private TaskDao td;
	
	@Autowired
	public TaskServiceImpl(TaskDao td) {
		this.td = td;
	}

	@Override
	public void insertTask(Task task) {
		td.insertTask(task);
	}

	@Override
	public List<Task> taskList(int projectId) {
		List<Task> taskList = td.getAllTask(projectId);
		return taskList;
	}

	@Override
	public void deleteTask(int taskId) {
		td.deleteTask(taskId);
	}

	@Override
	public List<Task> checkTaskWithUserName(int projectId, String startDate, String endDate, String userName) {
		List<Task> checkTaskList = td.checkTaskWithTaskName(projectId, startDate, endDate, userName);
		return checkTaskList;
	}

	@Override
	public void editTask(Task task, int taskId) {
		td.editTask(task, taskId);
	}

	@Override
	public List<TotalActualHour> checkTask(int projectId, String startDate, String endDate) {
		List<TotalActualHour> checkTaskList = td.checkTask(projectId, startDate, endDate);
		return checkTaskList;
	}
}
