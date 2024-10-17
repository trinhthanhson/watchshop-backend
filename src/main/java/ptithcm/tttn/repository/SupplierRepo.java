package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Supplier;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier,Long> {
}
