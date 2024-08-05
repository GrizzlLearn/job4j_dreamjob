package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Vacancy;
import java.util.Collection;
import java.util.Optional;

/**
 * @author dl
 * @date 22.07.2024 22:54
 */
public interface VacancyRepository {
	Vacancy save(Vacancy vacancy);

	boolean deleteById(int id);

	boolean update(Vacancy vacancy);

	Optional<Vacancy> findById(int id);

	Collection<Vacancy> findAll();
}
