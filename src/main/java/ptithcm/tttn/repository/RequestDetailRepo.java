package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Request_detail;

@Repository
public interface RequestDetailRepo extends JpaRepository<Request_detail,Long> {
}
