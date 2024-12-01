package com.wemade.coupon.dto.response;

import com.wemade.coupon.entity.Coupon;
import com.wemade.coupon.entity.CouponTopic;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GenerateCouponResponseDto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 16)
  private String code;

  private CouponTopic topic;

  @Column(nullable = false)
  private Boolean isRedeemed;

  private String description;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public GenerateCouponResponseDto(Coupon coupon) {
    this.id = coupon.getId();
    this.code = coupon.getCode();
    this.topic = coupon.getTopic();
    this.isRedeemed = coupon.getIsRedeemed();
    this.description = coupon.getDescription();
    this.createdAt = coupon.getCreatedAt();
    this.updatedAt = coupon.getUpdatedAt();
  }
}
