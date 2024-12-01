package com.wemade.coupon.repository;

import com.wemade.coupon.entity.Coupon;
import com.wemade.coupon.entity.CouponRedeemLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRedeemLogRepository extends JpaRepository<CouponRedeemLog, Long> {
  Optional<CouponRedeemLog> findByCouponAndUserId(Coupon coupon, String userId);
}
