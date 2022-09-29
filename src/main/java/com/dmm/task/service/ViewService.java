package com.dmm.task.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;

@Service
public class ViewService {
	@Autowired
	private TasksRepository tasksRepository;
	
	public List<List<LocalDate>> commonView(LocalDate date,List<LocalDate> week1,List<LocalDate> week2,List<LocalDate> week3,List<LocalDate> week4,
			List<LocalDate> week5,List<LocalDate> week6){
		LocalDate firstDayOfMonth = date.withDayOfMonth(1);
		int number = firstDayOfMonth.getDayOfWeek().getValue();
		LocalDate firstDayOfFirstWeek = firstDayOfMonth.minusDays(number);

		LocalDate lastDayOfMonth = firstDayOfMonth.plusDays(LocalDate.now().lengthOfMonth());
		int num = lastDayOfMonth.getDayOfWeek().getValue();
		LocalDate lastDayOfLastMonth = lastDayOfMonth.plusDays(6 - num);

		List<List<LocalDate>> matrix = new ArrayList<>();
		week1 = new ArrayList<>();
		week2 = new ArrayList<>();
		week3 = new ArrayList<>();
		week4 = new ArrayList<>();
		week5 = new ArrayList<>();

		for (int i = 0; i < 7; i++) {
			week1.add(firstDayOfFirstWeek.plusDays(i));
		}
		matrix.add(week1);

		for (int i = 7; i < 14; i++) {
			week2.add(firstDayOfFirstWeek.plusDays(i));
		}
		matrix.add(week2);

		for (int i = 14; i < 21; i++) {
			week3.add(firstDayOfFirstWeek.plusDays(i));
		}
		matrix.add(week3);

		for (int i = 21; i < 28; i++) {
			week4.add(firstDayOfFirstWeek.plusDays(i));
		}
		matrix.add(week4);

		for (int i = 28; i < 35; i++) {
			week5.add(firstDayOfFirstWeek.plusDays(i));
		}
		matrix.add(week5);

		double n = LocalDate.now().lengthOfMonth() - (7 - number);

		double switchNumber = n / 7;

		if (switchNumber > 4) {
			week6 = new ArrayList<>();
			for (int i = 35; i < 42; i++) {
				week6.add(firstDayOfFirstWeek.plusDays(i));
			}
			matrix.add(week6);
		} else {

		}
		
		return matrix;
		
	}
	
	public Map<LocalDate, List<Tasks>> adminMap(LocalDate date) {
		LocalDate firstDayOfMonth = date.withDayOfMonth(1);
		int number = firstDayOfMonth.getDayOfWeek().getValue();
		LocalDate firstDayOfFirstWeek = firstDayOfMonth.minusDays(number);

		LocalDate lastDayOfMonth = firstDayOfMonth.plusDays(date.lengthOfMonth());
		int num = lastDayOfMonth.getDayOfWeek().getValue();
		LocalDate lastDayOfLastMonth = lastDayOfMonth.plusDays(6 - num);

		//map作成
		Map<LocalDate, List<Tasks>> map = new TreeMap<>();

		int IntDay = (int) ChronoUnit.DAYS.between(firstDayOfFirstWeek, lastDayOfLastMonth) + 1;

		System.out.println(IntDay);

		LocalDateTime dateMap;
		LocalDateTime LDTfirstDayOfFirstWeek = firstDayOfFirstWeek.atStartOfDay();

		dateMap = LDTfirstDayOfFirstWeek;
		while (true) {
			LocalDateTime afterDay = dateMap.plusDays(1);
			dateMap = afterDay;
			LocalDate d = dateMap.toLocalDate();
			LocalTime t = LocalTime.of(23, 59, 59);
			LocalDateTime dt = LocalDateTime.of(d, t);
			map.put(d, tasksRepository.findByDateBetween(dateMap, dt));
			if (map.size() == IntDay) {
				break;
			}
		}
		
		return map;
	}
	
	public Map<LocalDate, List<Tasks>> userMap(LocalDate date,@AuthenticationPrincipal AccountUserDetails user) {
		LocalDate firstDayOfMonth = date.withDayOfMonth(1);
		int number = firstDayOfMonth.getDayOfWeek().getValue();
		LocalDate firstDayOfFirstWeek = firstDayOfMonth.minusDays(number);

		LocalDate lastDayOfMonth = firstDayOfMonth.plusDays(date.lengthOfMonth());
		int num = lastDayOfMonth.getDayOfWeek().getValue();
		LocalDate lastDayOfLastMonth = lastDayOfMonth.plusDays(6 - num);

		//map作成
		Map<LocalDate, List<Tasks>> map = new TreeMap<>();

		int IntDay = (int) ChronoUnit.DAYS.between(firstDayOfFirstWeek, lastDayOfLastMonth) + 1;

		System.out.println(IntDay);

		LocalDateTime dateMap;
		LocalDateTime LDTfirstDayOfFirstWeek = firstDayOfFirstWeek.atStartOfDay();

		dateMap = LDTfirstDayOfFirstWeek;
		while (true) {
			LocalDateTime afterDay = dateMap.plusDays(1);
			dateMap = afterDay;
			LocalDate d = dateMap.toLocalDate();
			LocalTime t = LocalTime.of(23, 59, 59);
			LocalDateTime dt = LocalDateTime.of(d, t);
			map.put(d, tasksRepository.findByDateBetween(dateMap, dt,user.getUsername()));
			if (map.size() == IntDay) {
				break;
			}
		}
		
		return map;
	}

}
