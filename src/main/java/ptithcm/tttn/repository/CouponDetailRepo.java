package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Coupon_detail;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CouponDetailRepo extends JpaRepository<Coupon_detail,Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM coupon_detail WHERE coupon_id = ?1",nativeQuery = true)
    void deleteByCouponId(Long coupon_id);

    @Query(value = "SELECT * FROM coupon_detail WHERE coupon_id = ?1",nativeQuery = true)
    List<Coupon_detail> findAllByCouponId(Long couponId);
}
