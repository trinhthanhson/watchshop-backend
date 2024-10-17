package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Order_detail;

@Repository
public interface OrderDetailRepo extends JpaRepository<Order_detail,Long> {
    @Query("SELECT SUM(quantity*price) FROM Order_detail WHERE order_id = ?1")
    int totalPriceByOrderId(Long cart_id);

    @Query("SELECT SUM(quantity) FROM Order_detail WHERE order_id = ?1")
    int totalQuantityByOrderId(Long cart_id);

}
