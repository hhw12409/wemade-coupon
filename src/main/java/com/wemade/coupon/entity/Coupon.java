package com.wemade.coupon.entity;

import com.wemade.coupon.utils.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
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
  @Schema(description = "쿠폰 ID", example = "1")
  private Long id;

  @Schema(description = "쿠폰 코드", example = "1eftHjRheCKMSeMT")
  private String code;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "topic_id", nullable = false)
  @Schema(description = "쿠폰 토픽 정보", example = "id, name, createdAt, isActive")
  private CouponTopic topic;

  @Schema(description = "쿠폰 주제", example = "오픈 이벤트 프로모션")
  private String topicName;

  @Schema(description = "쿠폰 내용", example = "오픈 이벤트 프로모션으로 발급되는 재화 지급 쿠폰 입니다.")
  private String description;

  @Schema(description = "쿠폰 생성일", example = "2024-12-02 05:57:34")
  private LocalDateTime createdAt;

  @Schema(description = "쿠폰 내용 업데이트일", example = "2024-12-02 05:57:34")
  private LocalDateTime updatedAt;

  public Coupon(String code, CouponTopic topic, String description) {
    this.code = code;
    this.topic = topic;
    this.topicName = topic.getName();
    this.description = description;
    this.createdAt = DateUtil.currentDateTime();
    this.updatedAt = DateUtil.currentDateTime();
  }
}
