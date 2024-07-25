package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.repository.CandidateRepository;
import ru.job4j.dreamjob.repository.MemoryCandidateRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author dl
 * @date 26.07.2024 00:27
 */
public class SimpleCandidateService implements CandidateService {

	private static final SimpleCandidateService INSTANCE = new SimpleCandidateService();

	private final CandidateRepository candidateRepository = MemoryCandidateRepository.getInstance();

	private SimpleCandidateService() { }

	public static SimpleCandidateService getInstance() {
		return INSTANCE;
	}

	@Override
	public Candidate save(Candidate candidate) {
		return candidateRepository.save(candidate);
	}

	@Override
	public Optional<Candidate> deleteById(int id) {
		return candidateRepository.deleteById(id);
	}

	@Override
	public boolean update(Candidate candidate) {
		return candidateRepository.update(candidate);
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
