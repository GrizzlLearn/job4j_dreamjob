package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Optional;

/**
 * @author dl
 * @date 26.07.2024 00:27
 */
public interface CandidateService {
	Candidate save(Candidate candidate);

	Optional<Candidate> deleteById(int id);

	boolean update(Candidate candidate);

	Optional<Candidate> findById(int id);

	Collection<Candidate> findAll();
}
