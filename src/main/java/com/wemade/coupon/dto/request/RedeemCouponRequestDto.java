package com.wemade.coupon.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RedeemCouponRequestDto {
  private String code;
  private String userId;
}
