package cf.inseoul.controller;

import cf.inseoul.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	private final ProductRepository productRepository;

	@Autowired
	public MainController(ProductRepository productRepository) {
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

	@GetMapping("/editorial")
	public String editorial() {
		return "editorial";
	}

	@GetMapping("/generic")
	public String generic() {
		return "generic";
	}

	@GetMapping("/elements")
	public String elements() {
		return "elements";
	}

	@GetMapping("/dbList")
	public String dbList(Model model) {
		model.addAttribute("products", productRepository.findAll());
		return "dbList";
	}
}
