package cf.inseoul.controller;

import cf.inseoul.repository.ProductRepository;
import cf.inseoul.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	private final ProductService productService;
	private final ProductRepository productRepository;

	@Autowired
	public MainController(ProductService productService, ProductRepository productRepository) {
		this.productService = productService;
		this.productRepository = productRepository;
	}

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("products", productRepository.findAll());
		return "index";
	}

	@GetMapping("/index")
	public String index() {
		return "redirect:/";
	}

	@GetMapping("/generic")
	public String generic() {
		return "generic";
	}

	@GetMapping("/elements")
	public String elements() {
		return "elements";
	}

	@GetMapping("/db")
	String DBList(Model model) {
		model.addAttribute("products", productRepository.findAll());
		return "db";
	}
}
