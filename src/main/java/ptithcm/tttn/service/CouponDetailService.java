package ptithcm.tttn.service;

import ptithcm.tttn.entity.Coupon_detail;

public interface CouponDetailService {
    Coupon_detail updateStatusDetail(Coupon_detail detail, Long coupon_detail_id) throws Exception;

    Coupon_detail findById(Long id) throws Exception;
}
