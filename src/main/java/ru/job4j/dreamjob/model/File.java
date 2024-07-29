package ru.job4j.dreamjob.model;

import lombok.Data;

import java.util.Objects;

/**
 * @author dl
 * @date 29.07.2024 20:26
 */
@Data
public class File {
	private int id;

	private String name;

	private String path;

	public File(String name, String path) {
		this.name = name;
		this.path = path;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		File file = (File) o;
		return id == file.id && Objects.equals(path, file.path);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, path);
	}
}
