package ru.job4j.dreamjob.dto;

import lombok.Data;

/**
 * @author dl
 * @date 29.07.2024 20:36
 */

@Data
public class FileDto {
	private String name;

	private byte[] content;

	public FileDto(String name, byte[] content) {
		this.name = name;
		this.content = content;
	}
}
