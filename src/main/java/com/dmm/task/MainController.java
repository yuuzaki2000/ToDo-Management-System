package com.dmm.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.service.AccountUserDetails;
import com.dmm.task.service.ViewService;

@Controller
public class MainController {

	@Autowired
	private TasksRepository tasksRepository;

	@Autowired
	private ViewService viewService;

	@GetMapping("/main")
	public String Main(@AuthenticationPrincipal AccountUserDetails user, Model model) {

		int thisYear = LocalDate.now().getYear();
		int thisMonth = LocalDate.now().getMonth().getValue();
		String month = thisYear + "年" + thisMonth +"月";
		model.addAttribute("month", month);
		
		LocalDate prevFirstDay = LocalDate.of(thisYear, thisMonth-1, 1);
		model.addAttribute("prev",prevFirstDay);
		
		LocalDate nextFirstDay = LocalDate.of(thisYear, thisMonth+1, 1);
		model.addAttribute("next",nextFirstDay);

		List<LocalDate> week1 = new ArrayList<>();
		List<LocalDate> week2 = new ArrayList<>();
		List<LocalDate> week3 = new ArrayList<>();
		List<LocalDate> week4 = new ArrayList<>();
		List<LocalDate> week5 = new ArrayList<>();
		List<LocalDate> week6 = new ArrayList<>();

		List<List<LocalDate>> matrix = viewService.commonView(LocalDate.now(), week1, week2, week3, week4, week5,
				week6);

		model.addAttribute("matrix", matrix);

		if (user.getName().equals("admin-name") == true) {
			Map<LocalDate, List<Tasks>> map = viewService.adminMap(LocalDate.now());

			model.addAttribute("tasks", map);

		} else if (user.getName().equals("user-name") == true) {

			//map作成
			Map<LocalDate, List<Tasks>> map = viewService.userMap(LocalDate.now(),user);

			model.addAttribute("tasks", map);
		}

		return "main";

	}

}
