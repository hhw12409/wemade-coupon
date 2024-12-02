package com.wemade.coupon.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "쿠폰 비활성화 요청 DTO")
public class DeactivateCouponRequestDto {

  @Schema(description = "쿠폰 주제 ID", example = "1")
  Long id;
}
