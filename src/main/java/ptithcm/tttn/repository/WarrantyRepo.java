package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Warranty;

@Repository
public interface WarrantyRepo extends JpaRepository<Warranty,Long> {
}
