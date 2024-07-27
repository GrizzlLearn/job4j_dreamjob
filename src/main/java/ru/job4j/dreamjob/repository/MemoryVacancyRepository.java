package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dl
 * @date 22.07.2024 22:55
 */
@ThreadSafe
@Repository
public class MemoryVacancyRepository implements VacancyRepository {

	private final AtomicInteger nextId = new AtomicInteger(0);

	private final Map<Integer, Vacancy> vacancies = new ConcurrentHashMap<>();

	private MemoryVacancyRepository() {
		save(new Vacancy(0, "Intern Java Developer", "description 1", LocalDateTime.now()));
		save(new Vacancy(0, "Junior Java Developer", "description 2", LocalDateTime.now()));
		save(new Vacancy(0, "Junior+ Java Developer", "description 3", LocalDateTime.now()));
		save(new Vacancy(0, "Middle Java Developer", "description 4", LocalDateTime.now()));
		save(new Vacancy(0, "Middle+ Java Developer", "description 5", LocalDateTime.now()));
		save(new Vacancy(0, "Senior Java Developer", "description 6", LocalDateTime.now()));
	}

	@Override
	public Vacancy save(Vacancy vacancy) {
		vacancy.setId(nextId.incrementAndGet());
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
