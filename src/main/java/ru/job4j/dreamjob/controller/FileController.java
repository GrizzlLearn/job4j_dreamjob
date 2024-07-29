package ru.job4j.dreamjob.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.dreamjob.service.FileService;

/**
 * @author dl
 * @date 29.07.2024 20:55
 */
@RestController
@RequestMapping("/files")
public class FileController {
	private final FileService fileService;

	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable int id) {
		var contentOptional = fileService.getFileId(id);
		if (contentOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(contentOptional.get().getContent());
	}
}
