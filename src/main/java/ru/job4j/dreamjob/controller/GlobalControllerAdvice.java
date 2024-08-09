package ru.job4j.dreamjob.controller;

import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.job4j.dreamjob.model.User;

/**
 * @author dl
 * @date 08.08.2024 22:55
 */
@ControllerAdvice
public class GlobalControllerAdvice {
	@ModelAttribute
	public void addUserToModel(Model model, @NotNull HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			user = new User();
			user.setName("Guest");
		}
		model.addAttribute("user", user);
	}
}
