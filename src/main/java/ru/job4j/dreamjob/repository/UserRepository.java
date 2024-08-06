package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.User;

import java.util.Optional;

/**
 * @author dl
 * @date 06.08.2024 21:39
 */
public interface UserRepository {

	Optional<User> save(User user);

	Optional<User> findByEmailAndPassword(String email, String password);
}
