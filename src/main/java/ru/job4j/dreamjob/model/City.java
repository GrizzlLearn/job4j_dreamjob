package ru.job4j.dreamjob.model;

import lombok.Data;

import java.util.Objects;

/**
 * @author dl
 * @date 28.07.2024 14:53
 */
@Data
public class City {
	private int id;
	private String name;

	public City() { }

	public City(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		City city = (City) o;
		return id == city.id;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
