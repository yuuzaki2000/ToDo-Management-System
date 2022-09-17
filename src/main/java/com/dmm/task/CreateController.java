package com.dmm.task;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreateController {
	@GetMapping("/main/create/{date}")
	public String getCreate() {
		return "create";
		
	}

}
