package ru.job4j.dreamjob.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author dl
 * @date 22.07.2024 23:37
 */
@Data
public class Candidate {
	private int id;
	private String name;
	private String description;
	private final LocalDateTime creationDate;

	public Candidate(int id, String name, String description, LocalDateTime creationDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.creationDate = creationDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Candidate candidate = (Candidate) o;
		return id == candidate.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
