package ru.job4j.dreamjob.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Map;

/**
 * @author dl
 * @date 22.07.2024 22:53
 */
public class Vacancy {

	public static final Map<String, String> COLUMN_MAPPING = Map.of(
			"id", "id",
			"title", "title",
			"description", "description",
			"creation_date", "creationDate",
			"visible", "visible",
			"city_id", "cityId",
			"file_id", "fileId"
	);

	private int id;
	private String title;
	private String description;
	private LocalDateTime creationDate = LocalDateTime.now();
	private boolean visible;
	private int cityId;
	private int fileId;

	public Vacancy() { }

	public Vacancy(int id, String title, String description, LocalDateTime creationDate, boolean visible, int cityId, int fileId) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.creationDate = creationDate;
		this.visible = visible;
		this.cityId = cityId;
		this.fileId = fileId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getCityId() {
		return this.cityId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getFileId() {
		return this.fileId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Vacancy vacancy = (Vacancy) o;
		return id == vacancy.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
