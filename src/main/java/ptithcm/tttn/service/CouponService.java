package ptithcm.tttn.service;

import ptithcm.tttn.entity.Coupon;
import ptithcm.tttn.entity.Coupon_detail;
import ptithcm.tttn.request.CouponRequest;

import java.util.List;

public interface CouponService {

    Coupon createCoupon(CouponRequest coupon, String jwt) throws Exception;

    List<Coupon> findAll() throws Exception;

    Coupon updateCoupon(Long id, CouponRequest coupon, String jwt) throws Exception;

    Coupon findById(Long id) throws Exception;

    void deleteCoupon(Long coupon_id);

    List<Coupon_detail> findAllDetailByCouponId(Long coupon_id);


}
