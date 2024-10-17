package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Cart_detail;

import java.util.List;

@Repository
public interface CartDetailRepo extends JpaRepository<Cart_detail,Long> {

    @Query(value = "SELECT * FROM cart_detail WHERE customer_id = ?1",nativeQuery = true)
    List<Cart_detail> findAllByCustomerId(Long customer_id);
}
