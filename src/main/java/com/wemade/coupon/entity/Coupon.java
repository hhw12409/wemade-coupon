package com.wemade.coupon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "coupon")
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
    this.code = this.code;
    this.topic = this.topic;
    this.userId = this.userId;
  }
}
