package ru.job4j.dreamjob.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.model.User;

/**
 * @author dl
 * @date 17.07.2024 23:23
 * Здесь return "index" возвращает шаблон с названием index.html
 */

@Controller
public class IndexController {

	@GetMapping({"/", "/index"})
	public String getIndex(Model model, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		if (user == null) {
			user = new User();
			user.setName("Guest");
		}
		model.addAttribute("user", user);
		return "index";
	}
}
