package com.wemade.coupon.service;

import com.wemade.coupon.dto.request.GenerateCouponRequestDto;
import com.wemade.coupon.dto.response.GenerateCouponResponseDto;
import com.wemade.coupon.entity.Coupon;
import com.wemade.coupon.entity.CouponTopic;
import com.wemade.coupon.exception.BadRequestException;
import com.wemade.coupon.exception.ExceptionCode;
import com.wemade.coupon.repository.CouponRepository;
import com.wemade.coupon.repository.CouponTopicRepository;
import com.wemade.coupon.utils.RandomUtil;
import lombok.RequiredArgsConstructor;
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
    // 쿠폰 생성 count가 0 이하일 경우 BadRequestException 발생
    if (request.getCount() <= 0) throw new BadRequestException(ExceptionCode.INVALID_REQUEST);

    // 1. CouponTopic을 찾거나 생성
    CouponTopic couponTopic = couponTopicRepository.findByName(request.getTopic())
            .orElseGet(() -> couponTopicRepository.save(new CouponTopic(request.getTopic())));

    // 2. count만큼 쿠폰 생성
    for (int i = 0; i < request.getCount(); i++) {
      String code = RandomUtil.generateRandomString();
      Coupon coupon = new Coupon(code, couponTopic, request.getUserId());
      couponRepository.save(coupon);
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
//    coupon.setIsRedeemed(true);
//    coupon.setUserId(userId);
    couponRepository.save(coupon);
    return ResponseEntity.ok().build();
  }

  @Transactional
  @Override
  public ResponseEntity<Void> deactivateCoupons(String topicName) {
    CouponTopic topic = couponTopicRepository.findByName(topicName)
            .orElseThrow(() -> new IllegalArgumentException("Topic not found."));
//    List<GenerateCouponResponseDto> coupons = couponRepository.findByTopic(topic);

//    for (GenerateCouponResponseDto coupon : coupons) {
//      coupon.setIsRedeemed(true);
      // couponRepository.save(coupon);
//    }
    return ResponseEntity.ok().build();
  }
}
