package com.wemade.coupon.repository;

import com.wemade.coupon.dto.response.GenerateCouponResponseDto;
import com.wemade.coupon.entity.Coupon;
import com.wemade.coupon.entity.CouponTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
  Optional<Coupon> findByCode(String code);
  List<GenerateCouponResponseDto> findByTopic(@Param("topic") CouponTopic topic);
}