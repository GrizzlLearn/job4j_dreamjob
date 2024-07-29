package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.File;

import java.util.Optional;

/**
 * @author dl
 * @date 29.07.2024 20:29
 */
public interface FileRepository {
	File save(File file);

	Optional<File> findById(int id);

	void deleteById(int id);
}
