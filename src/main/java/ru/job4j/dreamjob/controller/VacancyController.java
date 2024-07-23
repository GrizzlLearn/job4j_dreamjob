package ru.job4j.dreamjob.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.repository.MemoryVacancyRepository;
import ru.job4j.dreamjob.repository.VacancyRepository;

import java.time.LocalDateTime;

/**
 * @author dl
 * @date 22.07.2024 23:02
 */
@Controller
@RequestMapping("/vacancies")
public class VacancyController {
	public final VacancyRepository vacancyRepository = MemoryVacancyRepository.getInstance();

	@GetMapping
	public String getAll(Model model) {
		model.addAttribute("vacancies", vacancyRepository.findAll());
		return "vacancies/list";
	}

	@GetMapping("/create")
	public String create() {
		return "vacancies/create";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute Vacancy vacancy) {
		vacancyRepository.save(vacancy);
		return "redirect:/vacancies";
	}
}
