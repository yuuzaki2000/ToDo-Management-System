package com.dmm.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.form.TasksForm;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class MainController {

	@Autowired
	private TasksRepository tasksRepository;

	@GetMapping("/main")
	public String Main(@AuthenticationPrincipal AccountUserDetails user,Model model) {
		//カレンダーに年と月を記入
		LocalDate nowDate = LocalDate.now();
		int thisYear = nowDate.getYear();
		int thisMonth = nowDate.getMonth().getValue();
		String month = thisYear + "年" + thisMonth + "月";
		model.addAttribute("month", month);

		//カレンダーを表示するための処理を記載
		List<List<LocalDate>> matrix = new ArrayList<>();

		//その月の1日（ついたち）のLocalDateを取得する
		LocalDate firstOfMonth = LocalDate.now().withDayOfMonth(1);

		//その月の最後の日のLocalDateを取得する
		LocalDate lastDayOfMonth = firstOfMonth.plusDays(LocalDate.now().lengthOfMonth());

		//firstOfMonthの曜日をあらわすDayOfWeekを取得する
		int dowFOM = firstOfMonth.getDayOfWeek().getValue();

		//その月の1日（ついたち）の週の最初の日（日曜日）のLocalDateを取得する
		LocalDate fisrtDayOfFirstWeek = firstOfMonth.minusDays(dowFOM);

		//lastDayOfMonthの曜日をあらわすDayOfWeekを取得する
		int dowLDOM = lastDayOfMonth.getDayOfWeek().getValue();

		//その月の末日の週の最後の日(土曜日）のLocalDateを取得する
		LocalDate lastDayOfLastWeek = lastDayOfMonth.plusDays(6 - dowLDOM);

		//注目する日付のLocalDateを作成
		LocalDate date;
		


		//1週間目
		date = fisrtDayOfFirstWeek;		
		
		List<LocalDate> week1 = new ArrayList<LocalDate>();
		while (true) {
			LocalDate afterDay = date.plusDays(1);
			date = afterDay;
			week1.add(date);

			if (week1.size() == 7) {
				matrix.add(week1);
				break;
			}
		}
		//2週間目
		date = fisrtDayOfFirstWeek.plusDays(7);
		List<LocalDate> week2 = new ArrayList<LocalDate>();
		while (true) {
			LocalDate afterDay = date.plusDays(1);
			date = afterDay;
			week2.add(date);

			if (week2.size() == 7) {
				matrix.add(week2);
				break;
			}
		}
		//3週間目
		date = fisrtDayOfFirstWeek.plusDays(14);
		List<LocalDate> week3 = new ArrayList<LocalDate>();
		while (true) {
			LocalDate afterDay = date.plusDays(1);
			date = afterDay;
			week3.add(date);

			if (week3.size() == 7) {
				matrix.add(week3);
				break;
			}
		}
		//4週間目
		date = fisrtDayOfFirstWeek.plusDays(21);
		List<LocalDate> week4 = new ArrayList<LocalDate>();
		while (true) {
			LocalDate afterDay = date.plusDays(1);
			date = afterDay;
			week4.add(date);

			if (week4.size() == 7) {
				matrix.add(week4);
				break;
			}
		}
		//5週間目
		date = fisrtDayOfFirstWeek.plusDays(28);
		List<LocalDate> week5 = new ArrayList<LocalDate>();
		while (true) {
			LocalDate afterDay = date.plusDays(1);
			date = afterDay;
			week5.add(date);

			if (week5.size() == 7) {
				matrix.add(week5);
				break;
			}
		}
		
		//6週間目
		date = fisrtDayOfFirstWeek.plusDays(35);
		List<LocalDate> week6 = new ArrayList<LocalDate>();
		while (true) {
			if(lastDayOfMonth.compareTo(lastDayOfLastWeek)<=0) {
				break;
			}
			LocalDate afterDay = date.plusDays(1);
			date = afterDay;
			week6.add(date);

			if (week6.size() == 7) {
				matrix.add(week6);
				break;
			}
		}
		
		//map作成
		Map<LocalDate, List<Tasks>> map = new TreeMap<>();
				
		
		LocalDateTime dateMap;
		
		LocalDateTime LDTfirstDayOfFirstWeek = fisrtDayOfFirstWeek.atStartOfDay();
		
		dateMap = LDTfirstDayOfFirstWeek;
		while(true) {
			LocalDateTime afterDay = dateMap.plusDays(1);
			dateMap = afterDay;
			LocalDate d =dateMap.toLocalDate();
			map.put(d, tasksRepository.findByDateBetween(dateMap,dateMap.plusDays(1),user.getName()));
			if(date.compareTo(lastDayOfLastWeek)<=0) {
				break;
			}
		} 

		model.addAttribute("matrix", matrix);

		model.addAttribute("tasks", map);

		return "main";

	}

	@GetMapping("/main/create/{date}")
	public String create(@PathVariable String date, Model model) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate d = LocalDate.parse(date, dtf);
		model.addAttribute("date", d);

		return "create";

	}

	@GetMapping("/main/create")
	public String setForm(Model model) {
		TasksForm tasksForm = new TasksForm();
		model.addAttribute("tasksForm", tasksForm);
		return "create";

	}

	@PostMapping("/main/create")
	public String register(TasksForm tasksForm, @AuthenticationPrincipal AccountUserDetails user) {
		Tasks tasksDatabase = new Tasks();
		tasksDatabase.setTitle(tasksForm.getTitle());
		tasksDatabase.setName(user.getName());
		tasksDatabase.setText(tasksForm.getText());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateData = LocalDate.parse(tasksForm.getDate(), dtf);
		tasksDatabase.setDate(dateData);
		tasksDatabase.setDone(false);

		tasksRepository.save(tasksDatabase);

		return "redirect:/main";

	}

	@GetMapping("/main/edit/{id}")
	public String edit() {
		return "edit";

	}

	@PostMapping("/main/delete/{id}")
	public String delete(@PathVariable Integer id) {
		tasksRepository.deleteById(id);
		return "redirect:/edit";

	}

}
