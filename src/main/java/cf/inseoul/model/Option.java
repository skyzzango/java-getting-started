package cf.inseoul.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "option")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Option {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long optionId;

	@Column(nullable = false)
	private Long productId;

	@Column(nullable = false)
	private String optionName;

	@Column(nullable = false)
	private int price;

	// Getters and Setters ... (Omitted for brevity)
}
