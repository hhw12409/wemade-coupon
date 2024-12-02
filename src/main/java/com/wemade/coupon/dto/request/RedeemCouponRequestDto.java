package com.wemade.coupon.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "쿠폰 사용 요청 DTO")
public class RedeemCouponRequestDto {
  @Schema(description = "쿠폰 코드", example = "1eftHjRheCKMSeMT")
  private String code;

  @Schema(description = "사용하는 유저 ID", example = "hwanghw1114")
  private String userId;
}
