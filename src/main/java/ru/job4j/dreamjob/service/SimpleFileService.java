package ru.job4j.dreamjob.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.File;
import ru.job4j.dreamjob.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

/**
 * @author dl
 * @date 29.07.2024 20:39
 */
@Service
public class SimpleFileService implements FileService {

	private final FileRepository fileRepository;

	private final String storageDirectory;

	public SimpleFileService(FileRepository fileRepository,
	                         @Value("${file.directory}") String storageDirectory) {
		this.fileRepository = fileRepository;
		this.storageDirectory = storageDirectory;
		createStorageDirectory(storageDirectory);
	}

	private void createStorageDirectory(String path) {
		try {
			Files.createDirectories(Path.of(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String getNewFilePath(String sourceName) {
		return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
	}

	private void writeFileBytes(String path, byte[] content) {
		try {
			Files.write(Path.of(path), content);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private byte[] readFileAsBites(String path) {
		try {
			return Files.readAllBytes(Path.of(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void deleteFile(String path) {
		try {
			Files.deleteIfExists(Path.of(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public File save(FileDto fileDto) {
		String path = getNewFilePath(fileDto.getName());
		writeFileBytes(path, fileDto.getContent());
		return fileRepository.save(new File(fileDto.getName(), path));
	}

	@Override
	public Optional<FileDto> getFileId(int id) {
		var fileOptional = fileRepository.findById(id);
		if (fileOptional.isEmpty()) {
			return Optional.empty();
		}
		var content = readFileAsBites(fileOptional.get().getPath());
		return Optional.of(new FileDto(fileOptional.get().getName(), content));
	}

	@Override
	public void deleteBiId(int id) {
		var fileOptional = fileRepository.findById(id);
		if (fileOptional.isPresent()) {
			deleteFile(fileOptional.get().getPath());
			fileRepository.deleteById(id);
		}
	}
}
