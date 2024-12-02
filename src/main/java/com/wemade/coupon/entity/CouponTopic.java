package com.wemade.coupon.entity;

import com.wemade.coupon.utils.DateUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
@Entity
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
    this.createdAt = DateUtil.currentDateTime();
    this.isActive = true;
  }

  public void deactivate() {
    this.isActive = false;
  }
}
