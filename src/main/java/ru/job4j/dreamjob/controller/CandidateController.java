package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.UserService;

import java.io.IOException;
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
	private final UserService userService;

	public CandidateController(CandidateService candidateService, CityService cityService, UserService userService) {
		this.candidateService = candidateService;
		this.cityService = cityService;
		this.userService = userService;
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
	public String create(@ModelAttribute Candidate candidate, @RequestParam MultipartFile file, Model model) {
		try {
			candidateService.save(candidate, new FileDto(file.getOriginalFilename(), file.getBytes()));
			return "redirect:/candidates";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "errors/404";
		}
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
	public String update(@ModelAttribute Candidate candidate, @RequestParam MultipartFile file, Model model) {
		try {
			boolean isUpdated = candidateService.update(candidate, new FileDto(file.getOriginalFilename(), file.getBytes()));
			if (!isUpdated) {
				model.addAttribute("message", "Кандидат с указанным идентификатором не найден");
				return "errors/404";
			}
			return "redirect:/candidates";
		} catch (IOException e) {
			model.addAttribute("message", e.getMessage());
			return "errors/404";
		}
	}

	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable int id) {
		boolean idDeleted = candidateService.deleteById(id);
		if (!idDeleted) {
			model.addAttribute("message", "candidate with id " + id + " not found");
			return "errors/404";
		}
		return "redirect:/candidates";
	}
}
