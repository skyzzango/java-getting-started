package cf.inseoul.dto;

import cf.inseoul.model.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestProductDto {

	private Long categoryMainId;
	private Long categorySubId;
	private String productName;
	private String description;
	private int price;

	@Builder
	public RequestProductDto(Long categoryMainId, Long categorySubId, String productName, String description, int price) {
		this.categoryMainId = categoryMainId;
		this.categorySubId = categorySubId;
		this.productName = productName;
		this.description = description;
		this.price = price;
	}

	public Product toEntity() {
		return Product.builder()
				.categoryMainId(categoryMainId)
				.categorySubId(categorySubId)
				.productName(productName)
				.description(description)
				.price(price)
				.build();
	}
}
