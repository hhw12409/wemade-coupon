package com.wemade.coupon.entity;

import com.wemade.coupon.dto.request.GenerateCouponRequestDto;
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

  public CouponTopic(String name) {
    this.name = name;
  }
}
