package ru.job4j.dreamjob.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dl
 * @date 17.07.2024 23:23
 */

@RestController
public class IndexController {

	@GetMapping("/index")
	public String getIndex() {
		return "Hello World!";
	}
}
