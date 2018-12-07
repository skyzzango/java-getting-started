package cf.inseoul.controller;

import cf.inseoul.commons.util.ImageUploadUtils;
import cf.inseoul.model.Image;
import cf.inseoul.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class ImageController {

	private final ImageRepository imageRepository;

	@Autowired
	public ImageController(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	// 게시글 파일 업로드
	@PostMapping(value = "/image/upload", produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadFile(MultipartFile file, HttpServletRequest request) {
		ResponseEntity<String> entity;
		try {
			String savedFilePath = ImageUploadUtils.uploadFile(file, request);
			entity = new ResponseEntity<>(savedFilePath, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("exception: {}", e);
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 게시글 첨부파일 출력
	@GetMapping(value = "/image/display")
	public ResponseEntity<byte[]> displayFile(String fileName, HttpServletRequest request) {

		HttpHeaders httpHeaders = ImageUploadUtils.getHttpHeaders(fileName); // Http 헤더 설정 가져오기
		String rootPath = ImageUploadUtils.getRootPath(fileName, request); // 업로드 기본경로 경로

		ResponseEntity<byte[]> entity;

		// 파일데이터, HttpHeader 전송
		try (InputStream inputStream = new FileInputStream(rootPath + fileName)) {
			entity = new ResponseEntity<>(IOUtils.toByteArray(inputStream), httpHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("exception: {}", e);
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 게시글 파일 삭제 : 게시글 작성 페이지
	@PostMapping(value = "/image/delete/{id}")
	public ResponseEntity<String> deleteFile(@PathVariable("id") Long id, String fileName, HttpServletRequest request) {
		ResponseEntity<String> entity;

		try {
			ImageUploadUtils.deleteFile(fileName, request);
			imageRepository.deleteByImageName(fileName);
			entity = new ResponseEntity<>("DELETED", HttpStatus.OK);
		} catch (Exception e) {
			log.error("exception: {}", e);
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	// 게시글 첨부 파일 목록
	@GetMapping(value = "/image/list/{id}")
	public ResponseEntity<List<String>> getFiles(@PathVariable("id") Long id) {
		ResponseEntity<List<String>> entity;
		try {
			List<Image> imageList = imageRepository.findByProductIdOrderByUpdatedAtDesc(id);
			List<String> stringList = imageList.stream().map(Image::getLocation).collect(Collectors.toList());
			entity = new ResponseEntity<>(stringList, HttpStatus.OK);
		} catch (Exception e) {
			log.error("exception: {}", e);
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 게시글 파일 전체 삭제
	@PostMapping(value = "/image/deleteAll")
	public ResponseEntity<String> deleteAllFiles(@RequestParam("files[]") String[] files, HttpServletRequest request) {

		if (files == null || files.length == 0)
			return new ResponseEntity<>("DELETED", HttpStatus.OK);

		ResponseEntity<String> entity;

		try {
			for (String fileName : files)
				ImageUploadUtils.deleteFile(fileName, request);
			entity = new ResponseEntity<>("DELETED", HttpStatus.OK);
		} catch (Exception e) {
			log.error("exception: {}", e);
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return entity;
	}
}
