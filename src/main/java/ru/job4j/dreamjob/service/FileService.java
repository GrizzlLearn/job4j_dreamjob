package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.File;

import java.util.Optional;

/**
 * @author dl
 * @date 29.07.2024 20:37
 */
public interface FileService {
	File save(FileDto fileDto);

	Optional<FileDto> getFileId(int id);

	void deleteBiId(int id);
}
