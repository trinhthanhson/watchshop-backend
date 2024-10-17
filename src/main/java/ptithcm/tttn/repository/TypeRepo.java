package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Type;

@Repository
public interface TypeRepo extends JpaRepository<Type,Long> {
}
