package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.VacancyService;

import java.util.Optional;

/**
 * @author dl
 * @date 22.07.2024 23:02
 */
@Controller
@RequestMapping("/vacancies")
public class VacancyController {
	private final VacancyService vacancyService;

	private final CityService cityService;

	public VacancyController(VacancyService vacancyService, CityService cityService) {
		this.vacancyService = vacancyService;
		this.cityService = cityService;
	}

	@GetMapping
	public String getAll(Model model) {
		model.addAttribute("vacancies", vacancyService.findAll());
		return "vacancies/list";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("cities", cityService.findAll());
		return "vacancies/create";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute Vacancy vacancy) {
		vacancyService.save(vacancy);
		return "redirect:/vacancies";
	}

	@GetMapping("/{id}")
	public String getById(Model model, @PathVariable int id) {
		Optional<Vacancy> vacancyOptional = vacancyService.findById(id);
		if (vacancyOptional.isEmpty()) {
			model.addAttribute("message", "Vacancy with id " + id + " not found");
			return "errors/404";
		}
		model.addAttribute("cities", cityService.findAll());
		model.addAttribute("vacancy", vacancyOptional.get());
		return "vacancies/viewEdit";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute Vacancy vacancy, Model model) {
		boolean isUpdated = vacancyService.update(vacancy);
		if (!isUpdated) {
			model.addAttribute("message", "Vacancy with id " + vacancy.getId() + " not found");
			return "errors/404";
		}
		return "redirect:/vacancies";
	}

	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable int id) {
		Optional<Vacancy> isDeleted = vacancyService.deleteById(id);
		if (isDeleted.isEmpty()) {
			model.addAttribute("message", "Vacancy with id " + id + " not found");
			return "errors/404";
		}
		return "redirect:/vacancies";
	}
}
