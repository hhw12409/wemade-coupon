package com.wemade.coupon.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor
@Schema(description = "쿠폰 생성 요청 DTO")
public class GenerateCouponRequestDto {
  @Schema(description = "쿠폰 주제", example = "오픈 이벤트 프로모션")
  private String topic;

  @Schema(description = "쿠폰 수량", example = "10")
  private int count;

  @Schema(description = "쿠폰 내용", example = "오픈 이벤트 프로모션으로 발급되는 재화 지급 쿠폰 입니다.")
  private String description;
}
