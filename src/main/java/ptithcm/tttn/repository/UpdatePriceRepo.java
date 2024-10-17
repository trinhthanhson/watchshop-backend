package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Update_price;

@Repository
public interface UpdatePriceRepo extends JpaRepository<Update_price,Long> {

    @Query(value = "SELECT * FROM update_price WHERE product_id = ?1  ", nativeQuery = true)
    Update_price findByProductId(String product_id);
}
