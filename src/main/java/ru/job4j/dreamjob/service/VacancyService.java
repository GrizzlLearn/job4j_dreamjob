package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Vacancy;
import java.util.Collection;
import java.util.Optional;

/**
 * @author dl
 * @date 26.07.2024 00:08
 */
public interface VacancyService {
	Vacancy save(Vacancy vacancy);

	Optional<Vacancy> deleteById(int id);

	boolean update(Vacancy vacancy);

	Optional<Vacancy> findById(int id);

	Collection<Vacancy> findAll();
}
