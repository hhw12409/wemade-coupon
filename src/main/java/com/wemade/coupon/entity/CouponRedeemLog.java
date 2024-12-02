package com.wemade.coupon.entity;

import com.wemade.coupon.utils.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
@Entity
public class CouponRedeemLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "쿠폰 ID", example = "1")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coupon_id", nullable = false)
  private Coupon coupon;

  @Schema(description = "쿠폰 코드", example = "1eftHjRheCKMSeMT")
  private String code;

  @Schema(description = "사용한 유저 ID", example = "hwanghw1114")
  private String userId;

  @Schema(description = "쿠폰 사용한 날", example = "2024-12-02 05:57:34")
  private LocalDateTime redeemedAt;

  public CouponRedeemLog(Coupon coupon, String userId) {
    this.coupon = coupon;
    this.userId = userId;
    this.code = coupon.getCode();
    this.redeemedAt = DateUtil.currentDateTime();
  }
}
