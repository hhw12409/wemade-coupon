package com.wemade.coupon.dto.response;

import com.wemade.coupon.entity.Coupon;
import com.wemade.coupon.entity.CouponTopic;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Schema(description = "쿠폰 생성 응답 DTO")
public class GenerateCouponResponseDto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "쿠폰 ID", example = "1")
  private Long id;

  @Schema(description = "쿠폰 코드", example = "1eftHjRheCKMSeMT")
  private String code;

  @Schema(description = "쿠폰 토픽 정보", example = "id, name, createdAt, isActive")
  private CouponTopic topic;

  @Schema(description = "쿠폰 내용", example = "오픈 이벤트 프로모션으로 발급되는 재화 지급 쿠폰 입니다.")
  private String description;

  @Schema(description = "쿠폰 생성일", example = "2024-12-02 05:57:34")
  private LocalDateTime createdAt;

  @Schema(description = "쿠폰 내용 업데이트일", example = "2024-12-02 05:57:34")
  private LocalDateTime updatedAt;

  public GenerateCouponResponseDto(Coupon coupon) {
    this.id = coupon.getId();
    this.code = coupon.getCode();
    this.topic = coupon.getTopic();
    this.description = coupon.getDescription();
    this.createdAt = coupon.getCreatedAt();
    this.updatedAt = coupon.getUpdatedAt();
  }
}
