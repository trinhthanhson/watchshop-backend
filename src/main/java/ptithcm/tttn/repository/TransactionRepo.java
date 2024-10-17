package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
}
