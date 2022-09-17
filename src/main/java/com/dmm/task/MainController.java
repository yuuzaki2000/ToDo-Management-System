package com.dmm.task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/main")
	public String getMain(Model model) {
		Calendar c = Calendar.getInstance();
		int thisYear = c.get(Calendar.YEAR);
		int thisMonth = c.get(Calendar.MONTH)+1;
		String month = thisYear  +"年" + thisMonth +"月";
		model.addAttribute("month", month);
		
		LocalDate firstDate = LocalDate.now().withDayOfMonth(1);
		DayOfWeek firstDOW = firstDate.getDayOfWeek();
		int firstDOWInt =  firstDOW.getValue();
		
		System.out.println(firstDate.minusDays(firstDOWInt));
		
		
		return "main";
		
	}

}
