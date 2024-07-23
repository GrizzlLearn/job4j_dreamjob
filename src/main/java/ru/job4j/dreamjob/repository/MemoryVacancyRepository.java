package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Vacancy;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author dl
 * @date 22.07.2024 22:55
 */
public class MemoryVacancyRepository implements VacancyRepository {

	private static final MemoryVacancyRepository INSTANCE = new MemoryVacancyRepository();

	private int nextId = 1;

	private final Map<Integer, Vacancy> vacancies = new HashMap<>();

	private MemoryVacancyRepository() {
		save(new Vacancy(0, "Intern Java Developer", "description 1", LocalDateTime.now()));
		save(new Vacancy(0, "Junior Java Developer", "description 2", LocalDateTime.now()));
		save(new Vacancy(0, "Junior+ Java Developer", "description 3", LocalDateTime.now()));
		save(new Vacancy(0, "Middle Java Developer", "description 4", LocalDateTime.now()));
		save(new Vacancy(0, "Middle+ Java Developer", "description 5", LocalDateTime.now()));
		save(new Vacancy(0, "Senior Java Developer", "description 6", LocalDateTime.now()));
	}

	public static MemoryVacancyRepository getInstance() {
		return INSTANCE;
	}

	@Override
	public Vacancy save(Vacancy vacancy) {
		vacancy.setId(nextId++);
		vacancies.put(vacancy.getId(), vacancy);
		return vacancy;
	}

	@Override
	public Optional<Vacancy> deleteById(int id) {
		return Optional.ofNullable(vacancies.remove(id));
	}

	@Override
	public boolean update(Vacancy vacancy) {
		return vacancies.computeIfPresent(vacancy.getId(),
				(id, oldVacancy) -> new Vacancy(oldVacancy.getId(),
						vacancy.getTitle(),
						vacancy.getDescription(),
						vacancy.getCreationDate())) != null;
	}

	@Override
	public Optional<Vacancy> findById(int id) {
		return Optional.ofNullable(vacancies.get(id));
	}

	@Override
	public Collection<Vacancy> findAll() {
		return vacancies.values();
	}
}
