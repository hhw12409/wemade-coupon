package com.wemade.coupon.repository;

import com.wemade.coupon.entity.CouponTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponTopicRepository extends JpaRepository<CouponTopic, Long> {
  Optional<CouponTopic> findByName(String name);
}
