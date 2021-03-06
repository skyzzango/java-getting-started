package cf.inseoul.controller;

import cf.inseoul.dto.RequestProductDto;
import cf.inseoul.exception.ResourceNotFoundException;
import cf.inseoul.model.Image;
import cf.inseoul.model.Product;
import cf.inseoul.repository.ImageRepository;
import cf.inseoul.repository.ProductRepository;
import cf.inseoul.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ProductController {

	private final ImageRepository imageRepository;
	private final ProductRepository productRepository;
	private final StorageRepository storageRepository;

	@Autowired
	public ProductController(ImageRepository imageRepository, ProductRepository productRepository, StorageRepository storageRepository) {
		this.imageRepository = imageRepository;
		this.productRepository = productRepository;
		this.storageRepository = storageRepository;
	}

	@GetMapping("/product/register")
	public String moveRegister() {
		return "register";
	}

	@PostMapping("/product/register")
	public String registerProduct(@ModelAttribute RequestProductDto productDto) {
		Product product = productRepository.save(productDto.toEntity());
		if (!productDto.getFile().isEmpty()) {
			Image image = storageRepository.store(productDto.getFile());
			image.setProductId(product.getProductId());
			imageRepository.save(image);
			product.setLocation(image.getLocation());
			productRepository.save(product);
		}

		return "redirect:/product/details/" + product.getProductId();
	}

	@GetMapping("/product/upload")
	public String moveUpload() {
		return "upload";
	}

	@PostMapping("/product/upload")
	public String uploadProduct(@Valid @RequestBody Product product) {
		return "redirect:/product/details/" + productRepository.save(product).getProductId();
	}

	@GetMapping("/product/details/{productId}")
	public String getProductById(@PathVariable Long productId, Model model) {
		model.addAttribute("product", productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId)));
		return "details";
	}

	@GetMapping("/product/update/{productId}")
	public String moveUpdate(@PathVariable Long productId, Model model) {
		model.addAttribute("product", productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId)));
		return "update";
	}

	@PostMapping("/product/update/{productId}")
	public String updateProduct(@PathVariable(value = "productId") Long productId,
	                            @Valid @RequestBody Product productDetails) {

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		product.setProductName(productDetails.getProductName());
		product.setDescription(productDetails.getDescription());

		return "redirect:/product/details/" + productRepository.save(product).getProductId();
	}

	@GetMapping("/product/delete/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable(value = "productId") Long productId) {

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		productRepository.delete(product);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/product/list")
	public String getProductList(Model model) {
		model.addAttribute("products", productRepository.findAllByOrderByProductIdDesc());
		return "list";
	}
}
