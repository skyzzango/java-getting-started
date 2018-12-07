package cf.inseoul.dto;

import cf.inseoul.model.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RequestProductDto {

	private String categoryMain;
	private String categorySub;
	private String productName;
	private MultipartFile file;
	private int price;
	private String description;
	private String location;

	@Builder
	public RequestProductDto(String categoryMain, String categorySub, String productName,
	                         MultipartFile file, int price, String description, String location) {
		this.categoryMain = categoryMain;
		this.categorySub = categorySub;
		this.productName = productName;
		this.file = file;
		this.price = price;
		this.description = description;
		this.location = location;
	}

	public Product toEntity() {
		return Product.builder()
				.categoryMain(categoryMain)
				.categorySub(categorySub)
				.productName(productName)
				.description(description)
				.location(location)
				.price(price)
				.build();
	}
}
