package ru.job4j.dreamjob.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.repository.MemoryVacancyRepository;
import ru.job4j.dreamjob.repository.VacancyRepository;

import java.time.LocalDateTime;
import java.util.Optional;

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

	@GetMapping("/{id}")
	public String getById(Model model, @PathVariable int id) {
		Optional<Vacancy> vacancyOptional = vacancyRepository.findById(id);
		if (vacancyOptional.isEmpty()) {
			model.addAttribute("message", "Vacancy with id " + id + " not found");
			return "errors/404";
		}
		model.addAttribute("vacancy", vacancyOptional.get());
		return "vacancies/viewEdit";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute Vacancy vacancy, Model model) {
		boolean isUpdated = vacancyRepository.update(vacancy);
		if (!isUpdated) {
			model.addAttribute("message", "Vacancy with id " + vacancy.getId() + " not found");
			return "errors/404";
		}
		return "redirect:/vacancies";
	}

	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable int id) {
		Optional<Vacancy> isDeleted = vacancyRepository.deleteById(id);
		if (isDeleted.isEmpty()) {
			model.addAttribute("message", "Vacancy with id " + id + " not found");
			return "errors/404";
		}
		return "redirect:/vacancies";
	}
}
