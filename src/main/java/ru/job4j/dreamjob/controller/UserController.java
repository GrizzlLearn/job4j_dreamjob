package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import java.util.Optional;

/**
 * @author dl
 * @date 06.08.2024 21:40
 */
@Controller
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/register")
	public String getRegistationPage(Model model) {
		return "users/register";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute User user, Model model) {
		Optional<User> savedUser = userService.save(user);
		if (savedUser.isEmpty()) {
			model.addAttribute("message", "Пользователь с такой почтой уже существует");
			return "errors/404";
		}
		return "redirect:/vacancies";
	}
}
