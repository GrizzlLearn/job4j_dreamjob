package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
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

	public SimpleVacancyService(VacancyRepository vacancyRepository) {
		this.vacancyRepository = vacancyRepository;
	}

	@Override
	public Vacancy save(Vacancy vacancy) {
		return vacancyRepository.save(vacancy);
	}

	@Override
	public Optional<Vacancy> deleteById(int id) {
		return vacancyRepository.deleteById(id);
	}

	@Override
	public boolean update(Vacancy vacancy) {
		return vacancyRepository.update(vacancy);
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
