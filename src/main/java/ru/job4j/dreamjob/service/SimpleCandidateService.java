package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.repository.CandidateRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author dl
 * @date 26.07.2024 00:27
 */
@Service
public class SimpleCandidateService implements CandidateService {

	private final CandidateRepository candidateRepository;

	private final FileService fileService;

	public SimpleCandidateService(CandidateRepository sql2oCandidateRepository, FileService fileService) {
		this.candidateRepository = sql2oCandidateRepository;
		this.fileService = fileService;
	}

	@Override
	public Candidate save(Candidate candidate, FileDto fileDto) {
		saveNewFile(candidate, fileDto);
		return candidateRepository.save(candidate);
	}

	private void saveNewFile(Candidate candidate, FileDto fileDto) {
		var file = fileService.save(fileDto);
		candidate.setFileId(file.getId());
	}

	@Override
	public boolean deleteById(int id) {
		var fileOptional = findById(id);
		boolean result = false;
		if (fileOptional.isPresent()) {
			fileService.deleteBiId(fileOptional.get().getFileId());
			result = candidateRepository.deleteById(id);
		}
		return result;
	}

	@Override
	public boolean update(Candidate candidate, FileDto fileDto) {
		boolean isNewFileEmpty = fileDto.getContent().length == 0;
		if (isNewFileEmpty) {
			return candidateRepository.update(candidate);
		}
		int oldFileId = candidate.getFileId();
		saveNewFile(candidate, fileDto);
		boolean isUpdated = candidateRepository.update(candidate);
		fileService.deleteBiId(oldFileId);
		return isUpdated;
	}

	@Override
	public Optional<Candidate> findById(int id) {
		return candidateRepository.findById(id);
	}

	@Override
	public Collection<Candidate> findAll() {
		return candidateRepository.findAll();
	}
}
