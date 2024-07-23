package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.dreamjob.repository.CandidateRepository;
import ru.job4j.dreamjob.repository.MemoryCandidateRepository;

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
}
