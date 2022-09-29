package com.dmm.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
public class CreateController {
	
	@Autowired
	private TasksRepository tasksRepository;
	
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
		tasksDatabase.setName(user.getUsername());
		tasksDatabase.setText(tasksForm.getText());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateLD = LocalDate.parse(tasksForm.getDate(),dtf);
		LocalDateTime dateDate = dateLD.atStartOfDay();
		tasksDatabase.setDate(dateDate);
		tasksDatabase.setDone(false);

		tasksRepository.save(tasksDatabase);

		return "redirect:/main";

	}


}
