package com.wemade.coupon.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
public class GenerateCouponRequestDto {
  private String topic;
  private int count;
  private String description;
}
