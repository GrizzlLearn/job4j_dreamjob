package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Optional;

/**
 * @author dl
 * @date 01.08.2024 23:00
 */
@Repository
public class Sql2oCandidateRepository implements CandidateRepository {

	private Sql2o sql2o;

	public Sql2oCandidateRepository(Sql2o sql2o) {
		this.sql2o = sql2o;
	}

	@Override
	public Candidate save(Candidate candidate) {
		try (var connection = sql2o.open()) {
			String sql = """
						INSERT INTO candidates(name, description, creation_date, city_id, file_id)
						VALUES (:name, :description, :creationDate, :cityId, :fileId)
						""";
			var query = connection.createQuery(sql, true)
					.addParameter("name", candidate.getName())
					.addParameter("description", candidate.getDescription())
					.addParameter("creationDate", candidate.getCreationDate())
					.addParameter("cityId", candidate.getCityId())
					.addParameter("fileId", candidate.getFileId());
			int generatedId = query.executeUpdate().getKey(Integer.class);
			candidate.setId(generatedId);
			return candidate;
		}
	}

	@Override
	public Optional<Candidate> deleteById(int id) {
		try (var connection = sql2o.open()) {
			String sql = "SELECT * FROM candidates WHERE id = :id";
			var query = connection.createQuery(sql);
			var candidate = query
					.setColumnMappings(Candidate.COLUMN_MAPPING)
					.executeAndFetchFirst(Candidate.class);
			return Optional.ofNullable(candidate);
		}
	}

	@Override
	public boolean update(Candidate candidate) {
		try (var connection = sql2o.open()) {
			String sql = """
					UPDATE candidates
					SET name = :name, description = :description, creation_date = :creationDate,
					city_id = :cityId, file_id = :fileId
					WHERE id = :id
					""";
			var query = connection.createQuery(sql, true)
					.addParameter("name", candidate.getName())
					.addParameter("description", candidate.getDescription())
					.addParameter("creationDate", candidate.getCreationDate())
					.addParameter("cityId", candidate.getCityId())
					.addParameter("fileId", candidate.getFileId())
					.addParameter("id", candidate.getId());
			var affectedRows = query.executeUpdate().getResult();
			return affectedRows > 0;
		}
	}

	@Override
	public Optional<Candidate> findById(int id) {
		try (var connection = sql2o.open()) {
			String sql = "SELECT * FROM candidates WHERE id = :id";
			var query = connection.createQuery(sql);
			query.addParameter("id", id);
			var candidate = query.setColumnMappings(Candidate.COLUMN_MAPPING).executeAndFetchFirst(Candidate.class);
			return Optional.ofNullable(candidate);
		}
	}

	@Override
	public Collection<Candidate> findAll() {
		try (var connection = sql2o.open()) {
			String sql = "SELECT * FROM candidates";
			var query = connection.createQuery(sql);
			return query.setColumnMappings(Candidate.COLUMN_MAPPING).executeAndFetch(Candidate.class);
		}
	}
}
