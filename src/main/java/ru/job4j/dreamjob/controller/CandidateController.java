package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;

import java.util.Optional;

/**
 * @author dl
 * @date 23.07.2024 00:00
 */
@Controller
@RequestMapping("/candidates")
public class CandidateController {
	private final CandidateService candidateService;

	private final CityService cityService;

	public CandidateController(CandidateService candidateService, CityService cityService) {
		this.candidateService = candidateService;
		this.cityService = cityService;
	}

	@GetMapping
	public String getCandidates(Model model) {
		model.addAttribute("candidates", candidateService.findAll());
		return "candidates/list";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("cities", cityService.findAll());
		return "candidates/create";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute Candidate candidate) {
		candidateService.save(candidate);
		return "redirect:/candidates";
	}

	@GetMapping("/{id}")
	public String getById(Model model, @PathVariable int id) {
		Optional<Candidate> candidateOptional = candidateService.findById(id);
		if (candidateOptional.isEmpty()) {
			model.addAttribute("message", "candidate with id " + id + " not found");
			return "errors/404";
		}
		model.addAttribute("cities", cityService.findAll());
		model.addAttribute("candidate", candidateOptional.get());
		return "candidates/viewEdit";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute Candidate candidate, Model model) {
		boolean isUpdated = candidateService.update(candidate);
		if (!isUpdated) {
			model.addAttribute("message", "Candidate with id " + candidate.getId() + " not found");
			return "errors/404";
		}
		return "redirect:/candidates";
	}

	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable int id) {
		Optional<Candidate> idDeleted = candidateService.deleteById(id);
		if (idDeleted.isEmpty()) {
			model.addAttribute("message", "candidate with id " + id + " not found");
			return "errors/404";
		}
		return "redirect:/candidates";
	}
}
