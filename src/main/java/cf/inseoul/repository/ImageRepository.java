package cf.inseoul.repository;

import cf.inseoul.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	List<Image> findByProductIdOrderByUpdatedAtDesc(Long productId);

	void deleteByImageName(String name);
}
