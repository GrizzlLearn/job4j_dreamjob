package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Candidate;
import java.util.Collection;
import java.util.Optional;

/**
 * @author dl
 * @date 22.07.2024 23:50
 */
public interface CandidateRepository {
	Candidate save(Candidate candidate);

	Optional<Candidate> deleteById(int id);

	boolean update(Candidate candidate);

	Optional<Candidate> findById(int id);

	Collection<Candidate> findAll();
}
