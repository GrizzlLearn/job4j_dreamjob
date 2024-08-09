package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author dl
 * @date 17.07.2024 23:23
 * Здесь return "index" возвращает шаблон с названием index.html
 */

@Controller
public class IndexController {

	@GetMapping({"/", "/index"})
	public String getIndex() {
		return "index";
	}
}
