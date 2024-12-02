package com.wemade.coupon.service;

import com.wemade.coupon.dto.request.DeactivateCouponRequestDto;
import com.wemade.coupon.dto.request.GenerateCouponRequestDto;
import com.wemade.coupon.dto.request.RedeemCouponRequestDto;
import com.wemade.coupon.dto.response.GenerateCouponResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import java.util.List;

public interface CouponService {
  ResponseEntity<List<GenerateCouponResponseDto>> generateCoupons(GenerateCouponRequestDto request);
  ResponseEntity<Void> redeemCoupon(RedeemCouponRequestDto request);
  ResponseEntity<Void> deactivateCoupons(DeactivateCouponRequestDto request);
}
