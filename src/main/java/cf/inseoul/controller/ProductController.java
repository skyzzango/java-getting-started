package cf.inseoul.controller;

import cf.inseoul.exception.ResourceNotFoundException;
import cf.inseoul.model.Product;
import cf.inseoul.repository.ProductRepository;
import cf.inseoul.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class ProductController {

	private final ProductService productService;
	private final ProductRepository productRepository;

	@Autowired
	public ProductController(ProductService productService, ProductRepository productRepository) {
		this.productService = productService;
		this.productRepository = productRepository;
	}

	@GetMapping("/product/upload")
	public String moveUpload() {
		return "upload";
	}

	@PostMapping("/product/upload")
	public String uploadProduct(@Valid @RequestBody Product product) {
		return "redirect:/product/details/" + productRepository.save(product).getId();
	}

	@GetMapping("/product/details/{id}")
	public String getProductById(@PathVariable Long id, Model model) {
		model.addAttribute("product", productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id)));
		return "details";
	}

	@GetMapping("/product/update/{id}")
	public String moveUpdate(@PathVariable Long id, Model model) {
		model.addAttribute("product", productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id)));
		return "update";
	}

	@PostMapping("/product/update/{id}")
	public String updateProduct(@PathVariable(value = "id") Long id, @Valid @RequestBody Product productDetails) {

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

		product.setName(productDetails.getName());
		product.setDescription(productDetails.getDescription());

		return "redirect:/product/details/" + productRepository.save(product).getId();
	}

	@GetMapping("/product/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long id) {

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

		productRepository.delete(product);

		return ResponseEntity.ok().build();
	}
}
