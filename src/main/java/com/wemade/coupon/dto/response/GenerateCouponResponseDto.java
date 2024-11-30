package com.wemade.coupon.dto.response;

import com.wemade.coupon.entity.CouponTopic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GenerateCouponResponseDto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 16)
  private String code;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "topic_id", nullable = false)
  private CouponTopic topic;

  @Column(nullable = false)
  private Boolean isRedeemed;

  private String userId;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
