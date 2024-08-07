package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author dl
 * @date 06.08.2024 22:27
 */
@Repository
public class Sql2oUserRepository implements UserRepository {

	private static final Logger LOG = LoggerFactory.getLogger(Sql2oUserRepository.class.getName());
	private final Sql2o sql2o;

	public Sql2oUserRepository(Sql2o sql2o) {
		this.sql2o = sql2o;
	}

	@Override
	public Optional<User> save(User user) {
		Optional<User> result = Optional.empty();
		try (var connection = sql2o.open()) {
			String sql = """
						INSERT INTO users(email, name, password)
						VALUES (:email, :name, :password)
						""";
			var query = connection.createQuery(sql, true)
					.addParameter("email", user.getEmail())
					.addParameter("name", user.getName())
					.addParameter("password", user.getPassword());
			int generatedId = query.executeUpdate().getKey(Integer.class);
			user.setId(generatedId);
			result = Optional.of(user);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return result;
	}

	@Override
	public Optional<User> findByEmailAndPassword(String email, String password) {
		try (var connection = sql2o.open()) {
			String sql = "SELECT * FROM users WHERE email = :email AND password = :password";
			var querry = connection.createQuery(sql)
					.addParameter("email", email)
					.addParameter("password", password);
			return Optional.ofNullable(querry.executeAndFetchFirst(User.class));
		}
	}

	public Collection<User> getAllUsers() {
		try (var connection = sql2o.open()) {
			String sql = "SELECT * FROM users";
			var query = connection.createQuery(sql);
			return query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetch(User.class);
		}
	}

	public boolean deleteAll() {
		try (var connection = sql2o.open()) {
			String sql = "DELETE FROM users";
			var query = connection.createQuery(sql);
			var affectedRows = query.executeUpdate().getResult();
			return affectedRows > 0;
		}
	}
}
