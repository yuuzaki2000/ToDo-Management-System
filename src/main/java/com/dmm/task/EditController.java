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
import com.dmm.task.form.TaskForm;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class EditController {
	
	@Autowired TasksRepository tasksRepository;
	
	@GetMapping("/main/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		Tasks task = tasksRepository.findTasksById(id);
		model.addAttribute("task", task);

		return "edit";

	}
	
	@PostMapping("/main/edit/{id}")
	public String update(TaskForm taskForm,@AuthenticationPrincipal AccountUserDetails user,@PathVariable Integer id) {
		Tasks tasksDatabase = new Tasks();
		tasksDatabase.setTitle(taskForm.getTitle());
		tasksDatabase.setName(user.getUsername());
		tasksDatabase.setText(taskForm.getText());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateLD = LocalDate.parse(taskForm.getDate(),dtf);
		LocalDateTime dateDate = dateLD.atStartOfDay();
		tasksDatabase.setDate(dateDate);
		tasksDatabase.setDone(taskForm.isDone());
		
        tasksRepository.deleteById(id);
		tasksRepository.save(tasksDatabase);
		return "redirect:/main";
		
	}  

	@PostMapping("/main/delete/{id}")
	public String delete(@PathVariable Integer id) {
		tasksRepository.deleteById(id);
		return "redirect:/main";

	}


}
