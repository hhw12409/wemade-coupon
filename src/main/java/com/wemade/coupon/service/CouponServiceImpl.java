package com.wemade.coupon.service;

import com.wemade.coupon.dto.request.GenerateCouponRequestDto;
import com.wemade.coupon.dto.response.GenerateCouponResponseDto;
import com.wemade.coupon.entity.Coupon;
import com.wemade.coupon.entity.CouponTopic;
import com.wemade.coupon.repository.CouponRepository;
import com.wemade.coupon.repository.CouponTopicRepository;
import com.wemade.coupon.utils.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
  private final CouponRepository couponRepository;
  private final CouponTopicRepository couponTopicRepository;

  @Transactional
  @Override
  public ResponseEntity<List<GenerateCouponResponseDto>> generateCoupons(GenerateCouponRequestDto request) {

    if (request.getCount() <= 0) {
      throw new IllegalArgumentException("Count should be greater than 0.");
    }

    CouponTopic couponTopic = couponTopicRepository.findByName(request.getTopic())
            .orElseGet(() -> couponTopicRepository.save(new CouponTopic(request.getTopic())));

    for (int i = 0; i < request.getCount(); i++) {
      String code = RandomUtil.generateRandomString();
      couponRepository.save(new Coupon(code, couponTopic, request.getUserId()));
    }
    return new ResponseEntity<>(couponRepository.findByTopic(couponTopic), HttpStatus.OK);
  }

  @Transactional
  @Override
  public synchronized ResponseEntity<Void> redeemCoupon(String code, String userId) {
    Coupon coupon = couponRepository.findByCode(code)
            .orElseThrow(() -> new IllegalArgumentException("Invalid coupon code."));
    if (coupon.getIsRedeemed()) {
      throw new IllegalStateException("Coupon has already been redeemed.");
    }
    coupon.setIsRedeemed(true);
    coupon.setUserId(userId);
    couponRepository.save(coupon);
    return ResponseEntity.ok().build();
  }

  @Transactional
  @Override
  public ResponseEntity<Void> deactivateCoupons(String topicName) {
    CouponTopic topic = couponTopicRepository.findByName(topicName)
            .orElseThrow(() -> new IllegalArgumentException("Topic not found."));
    List<GenerateCouponResponseDto> coupons = couponRepository.findByTopic(topic);

    for (GenerateCouponResponseDto coupon : coupons) {
      coupon.setIsRedeemed(true);
      // couponRepository.save(coupon);
    }
    return ResponseEntity.ok().build();
  }
}
