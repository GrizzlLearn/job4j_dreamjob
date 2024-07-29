package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dl
 * @date 22.07.2024 23:52
 */
@ThreadSafe
@Repository
public class MemoryCandidateRepository implements CandidateRepository {

	private final AtomicInteger nextId = new AtomicInteger(0);

	private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

	private MemoryCandidateRepository() {
		save(new Candidate(0, "Candidate_1", "description_1", LocalDateTime.now(), 1, 0));
		save(new Candidate(0, "Candidate_2", "description_2", LocalDateTime.now(), 2, 0));
		save(new Candidate(0, "Candidate_3", "description_3", LocalDateTime.now(), 3, 0));
		save(new Candidate(0, "Candidate_4", "description_4", LocalDateTime.now(), 1, 0));
		save(new Candidate(0, "Candidate_5", "description_5", LocalDateTime.now(), 3, 0));
		save(new Candidate(0, "Candidate_6", "description_6", LocalDateTime.now(), 2, 0));
	}

	@Override
	public Candidate save(Candidate candidate) {
		candidate.setId(nextId.incrementAndGet());
		candidates.put(candidate.getId(), candidate);
		return candidate;
	}

	@Override
	public Optional<Candidate> deleteById(int id) {
		return Optional.ofNullable(candidates.get(id));
	}

	@Override
	public boolean update(Candidate candidate) {
		return candidates.computeIfPresent(candidate.getId(),
				(id, oldCandidate) -> new Candidate(oldCandidate.getId(),
						candidate.getName(),
						candidate.getDescription(),
						candidate.getCreationDate(),
						candidate.getCityId(),
						candidate.getFileId())) != null;
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
