package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Task;
import com.example.demo.entity.TotalActualHour;

public interface TaskDao {
	
	void insertTask(Task task);
	void deleteTask(int taskId);
	void editTask(Task task, int taskId);
	List<Task> getAllTask(int projectId);
	List<Task> checkTaskWithTaskName(int projectId, String startDate, String endDate, String userName);
	List<TotalActualHour> checkTask(int projectId, String startDate, String endDate);

}
