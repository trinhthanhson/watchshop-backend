package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Warranty_detail;

@Repository
public interface WarrantyDetailRepo extends JpaRepository<Warranty_detail,Long> {
}
