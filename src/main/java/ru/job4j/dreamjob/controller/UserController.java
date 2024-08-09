package ru.job4j.dreamjob.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
			return "users/register";
		}
		return "redirect:/vacancies";
	}

	@GetMapping("/login")
	public String getLoginPage(@ModelAttribute User user, HttpServletRequest request) {
		Optional<User> userOptional = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
		if (userOptional.isEmpty()) {
			return "users/login";
		}
		HttpSession session = request.getSession();
		session.setAttribute("user", userOptional.get());
		return "users/login";
	}

	@PostMapping("/login")
	public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
		Optional<User> userOptional = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
		if (userOptional.isEmpty()) {
			model.addAttribute("error", "Почта или пароль введены неверно");
			return "users/login";
		}
		HttpSession session = request.getSession();
		session.setAttribute("user", userOptional.get());
		return "redirect:/vacancies";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/users/login";
	}
}
