package cf.inseoul.commons.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageUploadUtils {

	private ImageUploadUtils() {
		throw new IllegalStateException("ImageUploadUtils class");
	}

	public static String uploadedImage(MultipartFile file) throws IOException {
			byte[] bytes = file.getBytes();
			Path path = Paths.get("/upload/" + file.getOriginalFilename());
			Path write = Files.write(path, bytes);
			System.out.println("path: " + path);
			System.out.println("write: " + write);
			return write.toString();
	}
}
