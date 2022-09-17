package com.dmm.task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.data.repository.TasksRepository;

@Controller
public class MainController {
	
	@Autowired
	private TasksRepository tasksRepository;
	
	@GetMapping("/main")
	public String getMain(Model model) {
		Calendar c = Calendar.getInstance();
		int thisYear = c.get(Calendar.YEAR);
		int thisMonth = c.get(Calendar.MONTH)+1;
		String month = thisYear  +"年" + thisMonth +"月";
		model.addAttribute("month", month);
		
		//カレンダーを表示するための処理を記載
		LocalDate firstDate = LocalDate.now().withDayOfMonth(1);
		DayOfWeek firstDOW = firstDate.getDayOfWeek();
		int firstDOWInt =  firstDOW.getValue();
		
		
		List<List<LocalDate>> matrix = new ArrayList<List<LocalDate>>();
		
		List<LocalDate> weeksFirst = new ArrayList<LocalDate>();
		List<LocalDate> weeksSecond = new ArrayList<LocalDate>();
		
		    LocalDate firstDayOfFirstWeek = firstDate.minusDays(firstDOWInt);
			for(int j=0;j<7;j++){
				weeksFirst.add(firstDayOfFirstWeek.plusDays(j));
			}
			matrix.add(weeksFirst);
			
			LocalDate firstDayOfSecondWeek = firstDate.minusDays(firstDOWInt).plusDays(7);
			for(int j=0;j<7;j++) {
			    weeksSecond.add(firstDayOfSecondWeek.plusDays(j));
			}
			matrix.add(weeksSecond);
		
		
		for(List<LocalDate> week:matrix) {
			for(LocalDate day:week) {
				System.out.println(day);
			}
		}
		
		
		//model.addAttribute("matrix",matrix);
		
		//tasksデータベースの要素を取得する
		//List<Tasks> tasks = tasksRepository.findAll();
		//model.addAttribute("tasks", tasks);
		
		return "main";
		
	}

}
