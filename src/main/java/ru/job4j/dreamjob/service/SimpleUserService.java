package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.repository.UserRepository;

import java.util.Optional;

/**
 * @author dl
 * @date 06.08.2024 22:46
 */
@Service
public class SimpleUserService implements UserService {

	private final UserRepository userRepository;

	public SimpleUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<User> save(User user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}
}
