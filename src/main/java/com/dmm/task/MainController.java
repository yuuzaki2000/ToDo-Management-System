package com.dmm.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;

@Controller
public class MainController {

	@Autowired
	private TasksRepository tasksRepository;

	@GetMapping("/main")
	public String getMain(Model model) {
		//カレンダーに年と月を記入
		LocalDate nowDate = LocalDate.now();
		int thisYear = nowDate.getYear();
		int thisMonth = nowDate.getMonth().getValue();
		String month = thisYear + "年" + thisMonth + "月";
		model.addAttribute("month", month);
		
List<List<LocalDate>> matrix = new ArrayList<>();
		
		//その月の1日（ついたち）のLocalDataを取得する
		LocalDate firstOfMonth = LocalDate.now().withDayOfMonth(1);
		
		//firstOfMonthの曜日をあらわすDayOfWeekを取得する
		int dowFOM = firstOfMonth.getDayOfWeek().getValue();
		
		//その月の1日（ついたち）の週の日曜日にあたる日のLocalDateを取得する
		LocalDate fisrtDayOfFirstWeek = firstOfMonth.minusDays(dowFOM);
		
		//注目する日付のLocalDateを作成
		LocalDate date;
		
		//1週間目
		date = fisrtDayOfFirstWeek;
		List<LocalDate> week1 = new ArrayList<LocalDate>();
		while(true) {
			LocalDate afterDay = date.plusDays(1);
			date = afterDay;
			week1.add(date);
			
			if(week1.size()==7) {
				matrix.add(week1);
				break;
			}
		}
		//2週間目
		date = fisrtDayOfFirstWeek.plusDays(7);		
		List<LocalDate> week2 = new ArrayList<LocalDate>();
		while(true) {
			LocalDate afterDay = date.plusDays(1);
			date = afterDay;
			week2.add(date);
			
			if(week2.size()==7) {
				matrix.add(week2);
				break;
			}
		}
		//3週間目
		date = fisrtDayOfFirstWeek.plusDays(14);		
		List<LocalDate> week3 = new ArrayList<LocalDate>();
		while(true) {
			LocalDate afterDay = date.plusDays(1);
			date = afterDay;
			week3.add(date);
			
			if(week3.size()==7) {
				matrix.add(week3);
				break;
			}
		}
		//4週間目
		date = fisrtDayOfFirstWeek.plusDays(21);		
		List<LocalDate> week4 = new ArrayList<LocalDate>();
		while(true) {
			LocalDate afterDay = date.plusDays(1);
			date = afterDay;
			week4.add(date);
			
			if(week4.size()==7) {
				matrix.add(week4);
				break;
			}
		}
		//5週間目
		date = fisrtDayOfFirstWeek.plusDays(28);		
		List<LocalDate> week5 = new ArrayList<LocalDate>();
		while(true) {
			LocalDate afterDay = date.plusDays(1);
			date = afterDay;
			week5.add(date);
			
			if(week5.size()==7) {
				matrix.add(week5);
				break;
			}
		}
		//6週間目
		date = fisrtDayOfFirstWeek.plusDays(35);		
		List<LocalDate> week6 = new ArrayList<LocalDate>();
		while(true) {
			LocalDate afterDay = date.plusDays(1);
			date = afterDay;
			week6.add(date);
			
			if(week6.size()==7) {
				matrix.add(week6);
				break;
			}
		}
		System.out.println(matrix);
		
		model.addAttribute("matrix", matrix);

		//tasksのデータのListを取得
		List<Tasks> lists = tasksRepository.findAll();
		model.addAttribute("tasks", lists);

		return "main";

	}

	@GetMapping("/main/create/{date}")
	public String create() {
		return "create";

	}

	@GetMapping("/main/edit/{id}")
	public String edit() {
		return "edit";

	}

}
