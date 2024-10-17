package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Transaction_detail;

@Repository
public interface TransactionDetailRepo extends JpaRepository<Transaction_detail,Long> {
}
