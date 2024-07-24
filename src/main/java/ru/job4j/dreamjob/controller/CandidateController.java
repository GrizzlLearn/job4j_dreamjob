package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.repository.CandidateRepository;
import ru.job4j.dreamjob.repository.MemoryCandidateRepository;
import java.util.Optional;

/**
 * @author dl
 * @date 23.07.2024 00:00
 */
@Controller
@RequestMapping("/candidates")
public class CandidateController {
	public final CandidateRepository candidateRepository = MemoryCandidateRepository.getInstance();

	@GetMapping
	public String getCandidates(Model model) {
		model.addAttribute("candidates", candidateRepository.findAll());
		return "candidates/list";
	}

	@GetMapping("/create")
	public String create() {
		return "candidates/create";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute Candidate candidate) {
		candidateRepository.save(candidate);
		return "redirect:/candidates";
	}

	@GetMapping("/{id}")
	public String getById(Model model, @PathVariable int id) {
		Optional<Candidate> candidateOptional = candidateRepository.findById(id);
		if (candidateOptional.isEmpty()) {
			model.addAttribute("message", "candidate with id " + id + " not found");
			return "errors/404";
		}
		model.addAttribute("candidate", candidateOptional.get());
		return "candidates/viewEdit";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute Candidate candidate, Model model) {
		boolean isUpdated = candidateRepository.update(candidate);
		if (!isUpdated) {
			model.addAttribute("message", "Candidate with id " + candidate.getId() + " not found");
			return "errors/404";
		}
		return "redirect:/candidates";
	}

	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable int id) {
		Optional<Candidate> idDeleted = candidateRepository.deleteById(id);
		if (idDeleted.isEmpty()) {
			model.addAttribute("message", "candidate with id " + id + " not found");
			return "errors/404";
		}
		return "redirect:/candidates";
	}
}
