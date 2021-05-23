package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Task;
import com.example.demo.entity.TotalActualHour;

public interface TaskService {
	
	void insertTask(Task task);
	void editTask(Task task, int taskId);
	void deleteTask(int taskId);
	List<Task> checkTaskWithUserName(int projectId, String startDate, String endDate, String userName);
	List<TotalActualHour> checkTask(int projectId, String startDate, String endDate);
	List<Task> taskList(int projectId);

}
