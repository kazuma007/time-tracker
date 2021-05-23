package com.example.demo.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Project;
import com.example.demo.entity.Task;
import com.example.demo.entity.TotalActualHour;
import com.example.demo.service.ProjectService;
import com.example.demo.service.TaskService;


@Controller
public class AfterLoginController {
	
	private final ProjectService projectService;
	private final TaskService taskService;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	public AfterLoginController(ProjectService projectService, TaskService taskService) {
		this.projectService = projectService;
		this.taskService = taskService;
	}
	
	// プロジェクト選択ページに遷移
	@RequestMapping("/")
	public String chooseProject(Model model) {
		model.addAttribute("getProjectForm", new GetProjectForm());
		return "chooseProject";
	}

	// プロジェクト作成ページに遷移
	@RequestMapping("/createProject")
	public String createProject(Model model) {
		model.addAttribute("projectCreateForm", new ProjectCreateForm());
		return "createPro";
	}
	
	// 工数確認ページに遷移
	@RequestMapping("/check")
	public String check(Model model) {
		// プロジェクトが選択されていない場合（セッションにprojectIdが登録されていない場合）、「プロジェクト選択」画面に遷移
		Integer projectIdTemp = (Integer) session.getAttribute("projectId");
		if(Objects.equals(projectIdTemp, null)) {
			model.addAttribute("getProjectForm", new GetProjectForm());
			return "chooseProject";
		}
		model.addAttribute("checkTaskForm", new CheckTaskForm());
		return "check";
	}
	
	@RequestMapping(value="/registerProject", method=RequestMethod.GET)
	public String goBackToProject(Model model) {
		model.addAttribute("getProjectForm", new GetProjectForm());
		return "chooseProject";
	}
	
	//　プロジェクトの作成
	@RequestMapping(value="/registerProject", method=RequestMethod.POST)
	public String registerProject(@Validated ProjectCreateForm projectCreateForm,
			BindingResult br, Model model, RedirectAttributes ra) {
		
		Project project = new Project();
		project.setProjectName(projectCreateForm.getProjectName());
		project.setContent(projectCreateForm.getContent());
		project.setProjectCode(projectCreateForm.getProjectCode());
		model.addAttribute("projectCreateForm", projectCreateForm);
		// プロジェクトコードが重複している場合、メッセージをセット
		try {
			projectService.registerProject(project);	
		}catch(Exception e) {
			model.addAttribute("msg_fail", "違うプロジェクトコードを設定してください。");
			return "createPro";
		}
		ra.addFlashAttribute("msg_success", "新しいプロジェクトを作成しました。");
		return "redirect:/createProject";
	}
	
	@RequestMapping(value="/project", method=RequestMethod.GET)
	public String getProject(Model model) {
		model.addAttribute("getProjectForm", new GetProjectForm());
		return "chooseProject";
	}
	
	// プロジェクトコードの判断＋一覧画面表示
	@RequestMapping(value="/project", method=RequestMethod.POST)
	public String getProject(@Validated GetProjectForm getProjectForm,
			BindingResult br, Model model, RedirectAttributes ra) {
//		if(br.hasErrors()) {
//			return "chooseProject";
//		}
		// プロジェクトコードが存在するかチェック
		Project project = projectService.getOneProject(getProjectForm.getProjectCode());
		if(Objects.equals(project, null)) {
			model.addAttribute("msg_error", "存在しないプロジェクトコードです。");
			return "chooseProject";
		}
		session.setAttribute("projectId", project.getProjectId());
		return "redirect:/task";
	}
	
	// プロジェクトコードの判断＋一覧画面表示
	@RequestMapping(value="/checkTask", method=RequestMethod.GET)
	public String checkTask(Model model) {
		model.addAttribute("getProjectForm", new GetProjectForm());
		return "chooseProject";
	}	
	
	// プロジェクトコードの判断＋一覧画面表示
	@RequestMapping(value="/checkTask", method=RequestMethod.POST)
	public String checkTask(@Validated CheckTaskForm checkTaskForm,
			BindingResult br, Model model) {
		// プロジェクトが選択されていない場合（セッションにprojectIdが登録されていない場合）、「プロジェクト選択」画面に遷移
		Integer projectIdTemp = (Integer) session.getAttribute("projectId");
		if(Objects.equals(projectIdTemp, null)) {
			model.addAttribute("getProjectForm", new GetProjectForm());
			return "chooseProject";
		}
		boolean flag = false;
		// 検索にヒットしたList<Task>を取得
		List<TotalActualHour> checkTaskList = new ArrayList<>();
		List<Task> checkTaskListWithUserName = new ArrayList<>();
		int projectId = projectIdTemp.intValue();
		if(Objects.equals(checkTaskForm.getUserName(), "")) {
			checkTaskList = taskService.checkTask(projectId, checkTaskForm.getStartDate(), checkTaskForm.getEndDate());
			flag = true;
		}else {
			checkTaskListWithUserName = taskService.checkTaskWithUserName(projectId, 
					checkTaskForm.getStartDate(), checkTaskForm.getEndDate(), checkTaskForm.getUserName());
		}
		// Usernameがありの時の処理
		if(flag == false) {
		// グラフに表示するために、タスクと予想工数、実績工数を配列に取得し、attributeに追加
			String taskNameList[] = new String[checkTaskListWithUserName.size()];
			double expectedManPowerList[] = new double[checkTaskListWithUserName.size()];
			double actualManPowerList[] = new double[checkTaskListWithUserName.size()];
			for(int i = 0; i < checkTaskListWithUserName.size(); i++) {
				taskNameList[i] = checkTaskListWithUserName.get(i).getTaskName();
				expectedManPowerList[i] =  checkTaskListWithUserName.get(i).getExpectedManPower();
				actualManPowerList[i] =  checkTaskListWithUserName.get(i).getActualManPower();
				System.out.println(taskNameList[i]);
			}
			model.addAttribute("taskNameList", taskNameList);
			model.addAttribute("expectedManPowerList", expectedManPowerList);
			model.addAttribute("actualManPowerList", actualManPowerList);
		}
		if(flag == true) {
		// グラフに表示するために、タスクと予想工数、実績工数を配列に取得し、attributeに追加
			String userNameList[] = new String[checkTaskList.size()];
			double totalActualManPowerList[] = new double[checkTaskList.size()];
			for(int i = 0; i < checkTaskList.size(); i++) {
				userNameList[i] = checkTaskList.get(i).getUserName();
				totalActualManPowerList[i] =  checkTaskList.get(i).getTotalActualManPower();
			}
			model.addAttribute("userNameList", userNameList);
			model.addAttribute("totalActualManPowerList", totalActualManPowerList);
		}
		// Usernameがなしの時の処理
		
		model.addAttribute("checkTaskList", checkTaskList);
		model.addAttribute("checkTaskListWithUserName", checkTaskListWithUserName);
		model.addAttribute("checkTaskForm", checkTaskForm);
		return "check";
	}
	
	// DBから値を取得＋一覧画面表示
	@RequestMapping("/task")
	public String task(Model model) {
		// プロジェクトが選択されていない場合（セッションにprojectIdが登録されていない場合）、「プロジェクト選択」画面に遷移
		Integer projectIdTemp = (Integer) session.getAttribute("projectId");
		if(Objects.equals(projectIdTemp, null)) {
			model.addAttribute("getProjectForm", new GetProjectForm());
			return "chooseProject";
		}
		int projectId = projectIdTemp.intValue();
		List<Task> taskList = taskService.taskList(projectId);
		session.setAttribute("taskList", taskList);
		return "index";
	}

	// タスクの「保存」ボタンを押下後の処理
	@RequestMapping(value="/editTask", method=RequestMethod.GET)
	public String editTask(Model model) {
		model.addAttribute("getProjectForm", new GetProjectForm());
		return "chooseProject";
	}	
	
	// タスクの「保存」ボタンを押下後の処理
	@RequestMapping(value="/editTask", method=RequestMethod.POST)
	public String editTask(@RequestParam int taskId,
			@Validated EditTaskForm editTaskForm,
			Model model, 
			RedirectAttributes ra) {
		// プロジェクトが選択されていない場合（セッションにprojectIdが登録されていない場合）、「プロジェクト選択」画面に遷移
		Integer projectIdTemp = (Integer) session.getAttribute("projectId");
		if(Objects.equals(projectIdTemp, null)) {
			model.addAttribute("getProjectForm", new GetProjectForm());
			return "chooseProject";
		}
		int projectId = projectIdTemp.intValue();
		Task task = new Task();
		task.setTaskName(editTaskForm.getTaskName());
		task.setExpectedManPower(editTaskForm.getExpectedManPower());
		task.setActualManPower(editTaskForm.getActualManPower());
		task.setStartDate(editTaskForm.getStartDate());
		task.setEndDate(editTaskForm.getEndDate());
		task.setUserName(editTaskForm.getUserName());
		taskService.editTask(task, taskId);
		return "redirect:/task";
	}
	
	
	// タスクの「削除」ボタンを押下後の処理
	@RequestMapping(value="/deleteTask", method=RequestMethod.GET)
	public String deleteTask(@RequestParam String taskId, Model model, 
			RedirectAttributes ra) {
		// DBに登録していないにもかかわらず、「削除」ボタンを押下した際に何も処理をせずにindexページに飛ばす
		if(Objects.equals(taskId, "")) {
			return "redirect:/task";
		}
		int taskIdFixed = Integer.parseInt(taskId);
		taskService.deleteTask(taskIdFixed);
		return "redirect:/task";
	}
	
	@RequestMapping(value="/addTask", method=RequestMethod.GET)
	public String addTask(Model model) {
		model.addAttribute("getProjectForm", new GetProjectForm());
		return "chooseProject";
	}
	
	// タスク追加→「保存」ボタンを押下後の処理
	@RequestMapping(value="/addTask", method=RequestMethod.POST)
	public String addTask(@Validated AddTaskForm addTaskForm,
			BindingResult br, Model model, RedirectAttributes ra) {
		// プロジェクトが選択されていない場合（セッションにprojectIdが登録されていない場合）、「プロジェクト選択」画面に遷移
		Integer projectIdTemp = (Integer) session.getAttribute("projectId");
		if(Objects.equals(projectIdTemp, null)) {
			model.addAttribute("getProjectForm", new GetProjectForm());
			return "chooseProject";
		}
		int projectId = projectIdTemp.intValue();
		Task task = new Task();
		task.setTaskName(addTaskForm.getTaskName());
		task.setExpectedManPower(addTaskForm.getExpectedManPower());
		task.setActualManPower(addTaskForm.getActualManPower());
		task.setStartDate(addTaskForm.getStartDate());
		task.setEndDate(addTaskForm.getEndDate());
		task.setUserName(addTaskForm.getUserName());
		task.setProjectId(projectId);
		taskService.insertTask(task);
		return "redirect:/task";
	}
}