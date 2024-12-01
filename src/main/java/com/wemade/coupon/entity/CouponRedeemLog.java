package com.wemade.coupon.entity;

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
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coupon_id", nullable = false)
  private Coupon coupon;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @Column(name = "redeemed_at", nullable = false, updatable = false)
  private LocalDateTime redeemedAt = LocalDateTime.now();

  public CouponRedeemLog(Coupon coupon, String userId) {
    this.coupon = coupon;
    this.userId = userId;
  }
}
