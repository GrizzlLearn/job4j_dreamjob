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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author dl
 * @date 22.07.2024 23:52
 */
@ThreadSafe
@Repository
public class MemoryCandidateRepository implements CandidateRepository {

	private final AtomicInteger nextId = new AtomicInteger(0);

	private final Map<Integer, AtomicReference<Candidate>> candidates = new ConcurrentHashMap<>();

	private MemoryCandidateRepository() {
		save(new Candidate(0, "Candidate_1", "description_1", LocalDateTime.now()));
		save(new Candidate(0, "Candidate_2", "description_2", LocalDateTime.now()));
		save(new Candidate(0, "Candidate_3", "description_3", LocalDateTime.now()));
		save(new Candidate(0, "Candidate_4", "description_4", LocalDateTime.now()));
		save(new Candidate(0, "Candidate_5", "description_5", LocalDateTime.now()));
		save(new Candidate(0, "Candidate_6", "description_6", LocalDateTime.now()));
	}

	@Override
	public Candidate save(Candidate candidate) {
		candidate.setId(nextId.incrementAndGet());
		candidates.put(candidate.getId(), new AtomicReference<>(candidate));
		return candidate;
	}

	@Override
	public Optional<Candidate> deleteById(int id) {
		AtomicReference<Candidate> oldRef = candidates.get(id);
		if (oldRef == null) {
			return Optional.empty();
		}

		while (true) {
			Candidate oldCandidate = oldRef.get();
			if (oldCandidate == null) {
				return Optional.empty();
			}
			if (oldRef.compareAndSet(oldCandidate, null)) {
				candidates.remove(id);
				return Optional.of(oldCandidate);
			}
		}
	}

	@Override
	public boolean update(Candidate candidate) {
		Optional<AtomicReference<Candidate>> oldCandidateAtomicReferenceExist = Optional.ofNullable(candidates.get(candidate.getId()));
		if (oldCandidateAtomicReferenceExist.isEmpty()) {
			return false;
		}
		while (true) {
			Optional<Candidate> oldCandidateExist = Optional.ofNullable(oldCandidateAtomicReferenceExist.get().get());
			if (oldCandidateExist.isEmpty()) {
				return false;
			}
			Candidate updatedCandidate = new Candidate(oldCandidateExist.get().getId(),
					candidate.getName(),
					candidate.getDescription(),
					candidate.getCreationDate());
			if (oldCandidateAtomicReferenceExist.get().compareAndSet(oldCandidateExist.get(), updatedCandidate)) {
				return true;
			}
		}
	}

	@Override
	public Optional<Candidate> findById(int id) {
		AtomicReference<Candidate> candidateAtomicReference = candidates.get(id);
		return candidateAtomicReference != null
				? Optional.ofNullable(candidateAtomicReference.get())
				: Optional.empty();
	}

	@Override
	public Collection<Candidate> findAll() {
		return candidates.values().stream()
				.map(AtomicReference::get)
				.collect(Collectors.toList());
	}
}
