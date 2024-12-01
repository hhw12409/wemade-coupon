package com.wemade.coupon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@ToString
@Entity
public class Coupon {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 16)
  private String code;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "topic_id", nullable = false)
  private CouponTopic topic;

  private Boolean isRedeemed;

  private String userId;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public Coupon(String code, CouponTopic topic, String userId) {
    this.code = code;
    this.topic = topic;
    this.isRedeemed = false;
    this.userId = userId;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }
}
