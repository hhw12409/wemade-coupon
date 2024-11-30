package com.wemade.coupon.service;

import com.wemade.coupon.dto.request.GenerateCouponRequestDto;
import com.wemade.coupon.dto.response.GenerateCouponResponseDto;
import com.wemade.coupon.entity.Coupon;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CouponService {
  ResponseEntity<List<GenerateCouponResponseDto>> generateCoupons(GenerateCouponRequestDto request);
  ResponseEntity<Void> redeemCoupon(String code, String userId);
  ResponseEntity<Void> deactivateCoupons(String topicName);
}
