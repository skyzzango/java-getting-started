package cf.inseoul.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "product")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Getter
@Setter
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@Column(nullable = false)
	private String productName;

	@Column(nullable = false)
	private String categoryMain;

	@Column(nullable = false)
	private String categorySub;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String description;

	@Column()
	private String location;

	@Column()
	private int price;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

	@Builder
	public Product(String productName, String categoryMain, String categorySub, String description, String location, int price) {
		this.productName = productName;
		this.categoryMain = categoryMain;
		this.categorySub = categorySub;
		this.description = description;
		this.location = location;
		this.price = price;
	}

	// Getters and Setters ... (Omitted for brevity)
}
