package cf.inseoul.service;

import cf.inseoul.config.StorageProperties;
import cf.inseoul.controller.FileUploadController;
import cf.inseoul.exception.StorageException;
import cf.inseoul.exception.StorageFileNotFoundException;
import cf.inseoul.model.Image;
import cf.inseoul.repository.ImageRepository;
import cf.inseoul.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class StorageService implements StorageRepository {

	private final Path rootLocation;
	private final ImageRepository imageRepository;

	@Autowired
	public StorageService(StorageProperties properties, ImageRepository imageRepository) {
		this.rootLocation = Paths.get(properties.getLocation());
		this.imageRepository = imageRepository;
	}

	@Override
	public Image store(MultipartFile file) {
		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
		String uuidFileName = getUuidFileName(originalFileName);
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to uploadedImage empty file " + uuidFileName);
			}
			if (uuidFileName.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot uploadedImage file with relative path outside current directory "
								+ uuidFileName);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.rootLocation.resolve(uuidFileName),
						StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new StorageException("Failed to uploadedImage file " + uuidFileName, e);
		}

		return Image.builder()
				.imageName(originalFileName)
				.location(MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "serveFile",
						this.rootLocation.resolve(uuidFileName).getFileName().toString()).build().toString())
				.build();
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	private static String getUuidFileName(String originalFileName) {
		return UUID.randomUUID().toString() + '_' + originalFileName;
	}
}
