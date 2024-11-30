package com.wemade.coupon.controller;

import com.wemade.coupon.annotation.LoggerTarget;
import com.wemade.coupon.dto.request.GenerateCouponRequestDto;
import com.wemade.coupon.entity.Coupon;
import com.wemade.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Coupon", description = "쿠폰 관련 API")
@RequestMapping("/api/coupon")
public class CouponController {

  private final CouponService couponService;

  @LoggerTarget
  @Operation(summary = "쿠폰 생성", description = "💡쿠폰코드는 사용자별 1회 사용이 가능합니다.\n쿠폰 코드는 숫자와 알파벳을 혼용하여 16자리로 구성됩니다.")
  @PostMapping("/generate")
  public List<Coupon> generateCoupon(@RequestBody GenerateCouponRequestDto request) {
    return couponService.generateCoupons(request);
  }

  @LoggerTarget
  @Operation(summary = "쿠폰 사용", description = "사용자가 쿠폰을 사용합니다.")
  @PostMapping("/redeem")
  public ResponseEntity<Void> redeemCoupon(
          @RequestBody String code,
          @RequestBody String userId
          ) {
    return couponService.redeemCoupon(code, userId);
  }

  @LoggerTarget
  @Operation(summary = "쿠폰 비활성화", description = "특정 주제의 쿠폰을 비활성화합니다.")
  @PostMapping("/deactivate")
  public ResponseEntity<Void> deactivateCoupons(
          @RequestBody String topic
  ) {
    return couponService.deactivateCoupons(topic);
  }
}
