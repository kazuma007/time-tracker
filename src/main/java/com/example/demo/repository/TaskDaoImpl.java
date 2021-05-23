package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.TotalActualHour;

@Repository
public class TaskDaoImpl implements TaskDao {
	
	private final JdbcTemplate jt;
	private final String insertTaskSql = 
			"insert into task(taskName, expectedManPower, actualManPower, startDate, "
			+ "endDate, userName, projectId) values (?,?,?,?,?,?,?)";
	private final String getAllTaskSql = 
			"select * from task where projectId = ?";
	private final String deleteSql = 
			"delete from task where taskId = ?";
	private final String checkTask = 
//			"select * from task where projectId = ?";
			"select userName, sum(actualManPower) as totalActualManPower from task where projectId = ?  and startDate >= ? and endDate <= ? group by userName";
	private final String checkTaskWithName =
			"select * from task where projectId = ? and userName = ? and startDate >= ? and endDate <= ?";
	private final String editTask =
			"update task SET taskName = ?, expectedManPower = ?, actualManPower = ?, "
			+ "startDate = ?, endDate = ?, userName = ? WHERE taskId = ?";
	
	@Autowired
	public TaskDaoImpl(JdbcTemplate jt) {
		this.jt = jt;
	}
	
	// insert時の処理
	@Override
	public void insertTask(Task task) {
		jt.update(insertTaskSql, task.getTaskName(), task.getExpectedManPower(), 
				task.getActualManPower(), task.getStartDate(), task.getEndDate(),
				task.getUserName(), task.getProjectId());
	}

	// タスク一覧取得
	@Override
	public List<Task> getAllTask(int projectId) {
		List<Map<String, Object>> taskListFromDb = jt.queryForList(getAllTaskSql, projectId);
		List<Task> taskList = new ArrayList<Task>();
		for(Map<String, Object> result : taskListFromDb) {
			Task task = new Task();
			task.setTaskId((int) result.get("taskId"));
			task.setTaskName((String) result.get("taskName"));
			task.setExpectedManPower((double) result.get("expectedManPower"));
			task.setActualManPower((double) result.get("actualManPower"));
			task.setStartDate((String) result.get("startDate"));
			task.setEndDate((String) result.get("endDate"));
			task.setUserName((String) result.get("userName"));
			task.setProjectId((int) result.get("projectId"));
			taskList.add(task);
		}
		return taskList;
	}

	// タスク削除時の処理
	@Override
	public void deleteTask(int taskId) {
		jt.update(deleteSql, taskId);
	}
	
	// 工数確認での検索結果表示をするためにcheckTaskListにTask型オブジェクト詰め込む
	// DBに日付をString型で格納してしまったため、全件取得後、期間の比較をしている
	@Override
	public List<Task> checkTaskWithTaskName(int projectId, String startDate, String endDate, String userName) {
		List<Map<String, Object>> checkTaskListFromDb;
		List<Task> checkTaskList = new ArrayList<>();
		String startDateFixed = fixStartDate(startDate);
		String endDateFixed = fixEndDate(endDate);
		System.out.print(startDateFixed);
		System.out.print(endDateFixed);
        checkTaskListFromDb = jt.queryForList(checkTaskWithName, projectId, userName, startDateFixed, endDateFixed);
        // DBの開始日（終了日）と入力値の開始日（終了日）を比較した上で、
        // trueだったら、TaskオブジェクトをcheckTaskListに追加
        for(Map<String, Object> result : checkTaskListFromDb) {
			Task task = new Task();
			task.setTaskId((int) result.get("taskId"));
			task.setTaskName((String) result.get("taskName"));
			task.setExpectedManPower((double) result.get("expectedManPower"));
			task.setActualManPower((double) result.get("actualManPower"));
			double diffBetExAc = (double) result.get("expectedManPower") - (double) result.get("actualManPower");
			task.setDiffExAc((double)Math.round(diffBetExAc * 10)/10);
			task.setStartDate((String) result.get("startDate"));
			task.setEndDate((String) result.get("endDate"));
			task.setUserName((String) result.get("userName"));
			task.setProjectId((int) result.get("projectId"));
			checkTaskList.add(task);
		}
		return checkTaskList;
	}

	// update時の処理
	@Override
	public void editTask(Task task, int taskId) {
		jt.update(editTask, task.getTaskName(), task.getExpectedManPower(), task.getActualManPower(),
				task.getStartDate(), task.getEndDate(), task.getUserName(), taskId);
	}

	// もし「終了日」が未入力の場合、1970-01-01をセット
	public String fixStartDate(String startDate) {
		String startDateFixed = "1970-01-01";
        if(!(Objects.equals(startDate, ""))) {
			startDateFixed = startDate;
        }
		return startDateFixed;
	}
	
	// もし「終了日」が未入力の場合、2100-01-01をセット
	public String fixEndDate(String endDate) {
    	String endDateFixed = "2100-01-01";
        if(!(Objects.equals(endDate, ""))) {
        	endDateFixed = endDate;
        }
        return endDateFixed;
	}
	
	// 各メンバーの合計値を取得
	// "select userName, sum(actualManPower) as totalActualManPower from task where projectId = ?  and startDate >= ? and endDate <= ? group by userName";
	@Override
	public List<TotalActualHour> checkTask(int projectId, String startDate, String endDate) {
		String startDateFixed = fixStartDate(startDate);
		String endDateFixed = fixEndDate(endDate);
		List<Map<String, Object>> checkTaskTotalListFromDb = jt.queryForList(checkTask, projectId, startDateFixed, endDateFixed);
		List<TotalActualHour> totalActualHourList = new ArrayList<>();
		for(Map<String, Object> result : checkTaskTotalListFromDb) {
			TotalActualHour totalActualHour = new TotalActualHour();
			totalActualHour.setUserName((String) result.get("userName"));
			totalActualHour.setTotalActualManPower((double) result.get("totalActualManPower"));
			totalActualHourList.add(totalActualHour);
		}
		return totalActualHourList;
	}
}
