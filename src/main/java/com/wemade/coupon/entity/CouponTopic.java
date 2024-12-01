package com.wemade.coupon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;



@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "coupon_topic")
public class CouponTopic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  private LocalDateTime createdAt;

  private Boolean isActive;

  public CouponTopic(String name) {
    this.name = name;
    this.createdAt = LocalDateTime.now();
    this.isActive = true;
  }

  public void deactivate() {
    this.isActive = false;
  }
}
