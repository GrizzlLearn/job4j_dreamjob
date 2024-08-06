package ru.job4j.dreamjob.model;

import lombok.Data;

import java.util.Map;
import java.util.Objects;

/**
 * @author dl
 * @date 06.08.2024 21:37
 */
@Data
public class User {

	public static final Map<String, String> COLUMN_MAPPING = Map.of(
			"id", "id",
			"email", "email",
			"name", "name",
			"password", "password"
	);

	private int id;
	private String email;
	private String name;
	private String password;

	public User() { }

	public User(int id, String email, String name, String password) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		return id == user.id;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
