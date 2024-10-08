package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.repository.VacancyRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author dl
 * @date 26.07.2024 00:12
 */
@Service
public class SimpleVacancyService implements VacancyService {

	private final VacancyRepository vacancyRepository;

	private final FileService fileService;

	public SimpleVacancyService(VacancyRepository sql2oVacancyRepository, FileService fileService) {
		this.vacancyRepository = sql2oVacancyRepository;
		this.fileService = fileService;
	}

	@Override
	public Vacancy save(Vacancy vacancy, FileDto fileDto) {
		saveNewFile(vacancy, fileDto);
		return vacancyRepository.save(vacancy);
	}

	private void saveNewFile(Vacancy vacancy, FileDto fileDto) {
		var file = fileService.save(fileDto);
		vacancy.setFileId(file.getId());
	}

	@Override
	public boolean deleteById(int id) {
		var fileOptional = findById(id);
		boolean result = false;
		if (fileOptional.isPresent()) {
			fileService.deleteBiId(fileOptional.get().getFileId());
			result = true;
		}
		return result;
	}

	@Override
	public boolean update(Vacancy vacancy, FileDto fileDto) {
		boolean isNewFileEmpty = fileDto.getContent().length == 0;
		if (isNewFileEmpty) {
			return vacancyRepository.update(vacancy);
		}
		int oldFileId = vacancy.getFileId();
		saveNewFile(vacancy, fileDto);
		boolean isUpdated = vacancyRepository.update(vacancy);
		fileService.deleteBiId(oldFileId);
		return isUpdated;
	}

	@Override
	public Optional<Vacancy> findById(int id) {
		return vacancyRepository.findById(id);
	}

	@Override
	public Collection<Vacancy> findAll() {
		return vacancyRepository.findAll();
	}
}
