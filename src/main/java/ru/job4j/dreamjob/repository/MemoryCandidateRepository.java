package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author dl
 * @date 22.07.2024 23:52
 */
@Repository
public class MemoryCandidateRepository implements CandidateRepository {
	private static final MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();

	private int nextId = 1;

	private final Map<Integer, Candidate> candidates = new HashMap<>();

	private MemoryCandidateRepository() {
		save(new Candidate(0, "Candidate_1", "description_1", LocalDateTime.now()));
		save(new Candidate(0, "Candidate_2", "description_2", LocalDateTime.now()));
		save(new Candidate(0, "Candidate_3", "description_3", LocalDateTime.now()));
		save(new Candidate(0, "Candidate_4", "description_4", LocalDateTime.now()));
		save(new Candidate(0, "Candidate_5", "description_5", LocalDateTime.now()));
		save(new Candidate(0, "Candidate_6", "description_6", LocalDateTime.now()));
	}

	public static MemoryCandidateRepository getInstance() {
		return INSTANCE;
	}

	@Override
	public Candidate save(Candidate candidate) {
		candidate.setId(nextId++);
		candidates.put(candidate.getId(), candidate);
		return candidate;
	}

	@Override
	public Optional<Candidate> deleteById(int id) {
		return Optional.ofNullable(candidates.remove(id));
	}

	@Override
	public boolean update(Candidate candidate) {
		return candidates.computeIfPresent(candidate.getId(),
				(id, oldCandidate) -> new Candidate(oldCandidate.getId(),
						candidate.getName(),
						candidate.getDescription(),
						candidate.getCreationDate())) != null;
	}

	@Override
	public Optional<Candidate> findById(int id) {
		return Optional.ofNullable(candidates.get(id));
	}

	@Override
	public Collection<Candidate> findAll() {
		return candidates.values();
	}
}
