package cf.inseoul.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "category")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;

	@Column(nullable = false)
	private String categoryName;

	@Column(nullable = false)
	private String category_parent;

	// Getters and Setters ... (Omitted for brevity)
}
