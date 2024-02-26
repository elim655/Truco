package com.cs309.tutorial.tests;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class TestController {

	HashMap<String, Courses> schedule = new  HashMap<>();

	@GetMapping("/")
	public String welcome() {
		return "Create Schedule";
	}

	@GetMapping("/schedule")
	public @ResponseBody HashMap<String,Courses> getSchedule() {
		return schedule;
	}


	@PostMapping("/add")
	public @ResponseBody String postCourse(@RequestBody Courses course) {
		System.out.println(course);
		schedule.put(course.getCourseNumber(), course);
		return "New class added "+ course.getCourse() + course.getCourseNumber();
	}

	@DeleteMapping("/remove/{course}")
	public @ResponseBody HashMap<String, Courses> deleteCourse(@PathVariable String course) {
		schedule.remove(course);
		return schedule;
	}


}
