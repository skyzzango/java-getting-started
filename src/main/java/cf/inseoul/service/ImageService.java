package cf.inseoul.service;

import cf.inseoul.model.Image;
import cf.inseoul.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ImageService {

	private ImageRepository imageRepository;

	@Transactional
	public Image imageSave(Image image) {
		return imageRepository.save(image);
	}
}
