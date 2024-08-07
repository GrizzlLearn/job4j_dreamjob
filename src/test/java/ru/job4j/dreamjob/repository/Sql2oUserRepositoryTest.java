package ru.job4j.dreamjob.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.User;

import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author dl
 * @date 07.08.2024 21:50
 */
public class Sql2oUserRepositoryTest {
	private static Sql2oUserRepository sql2oUserRepository;

	@BeforeAll
	public static void initRepositories() throws Exception {
		var properties = new Properties();
		try (var inputStream = Sql2oCandidateRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
			properties.load(inputStream);
		}
		var url = properties.getProperty("datasource.url");
		var username = properties.getProperty("datasource.username");
		var password = properties.getProperty("datasource.password");

		var configuration = new DatasourceConfiguration();
		var datasource = configuration.connectionPool(url, username, password);
		var sql2o = configuration.databaseClient(datasource);

		sql2oUserRepository = new Sql2oUserRepository(sql2o);
	}

	@AfterEach
	public void clearUsers() {
		sql2oUserRepository.deleteAll();
	}

	@Test
	public void addUsersThenUsersTableHasTwoLines() {
		User anotherUser1 = new User(0, "anotherUser1@email", "anotherUser1", "anotherPassword1");
		User anotherUser2 = new User(0, "anotherUser2@email", "anotherUser2", "anotherPassword2");
		sql2oUserRepository.save(anotherUser1);
		sql2oUserRepository.save(anotherUser2);
		var result = sql2oUserRepository.getAllUsers();
		assertThat(result).isEqualTo(List.of(anotherUser1, anotherUser2));
	}

	@Test
	public void addUserWithDuplicateEmailThenTableHasOneLine() {
		User anotherUser1 = new User(0, "anotherUser1@email", "anotherUser1", "anotherPassword1");
		User anotherUser2 = new User(0, "anotherUser1@email", "anotherUser2", "anotherPassword2");
		sql2oUserRepository.save(anotherUser1);
		int usersSize = 1;
		assertThat(sql2oUserRepository.save(anotherUser2).isEmpty()).isTrue();
		assertThat(sql2oUserRepository.getAllUsers().size()).isEqualTo(usersSize);
	}

	@Test
	public void whenDeleteAllUserThenUsersTableAreEmpty() {
		User anotherUser1 = new User(0, "anotherUser1@email", "anotherUser1", "anotherPassword1");
		User anotherUser2 = new User(0, "anotherUser2@email", "anotherUser2", "anotherPassword2");
		sql2oUserRepository.save(anotherUser1);
		sql2oUserRepository.save(anotherUser2);
		sql2oUserRepository.deleteAll();
		assertThat(sql2oUserRepository.getAllUsers()).isEqualTo(List.of());
	}
}
