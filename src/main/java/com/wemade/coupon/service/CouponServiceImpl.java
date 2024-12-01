package com.wemade.coupon.service;

import com.wemade.coupon.dto.request.DeactivateCouponRequestDto;
import com.wemade.coupon.dto.request.GenerateCouponRequestDto;
import com.wemade.coupon.dto.request.RedeemCouponRequestDto;
import com.wemade.coupon.dto.response.GenerateCouponResponseDto;
import com.wemade.coupon.entity.Coupon;
import com.wemade.coupon.entity.CouponRedeemLog;
import com.wemade.coupon.entity.CouponTopic;
import com.wemade.coupon.repository.CouponRedeemLogRepository;
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
  private final CouponRedeemLogRepository couponRedemptionRepository;

  @Transactional
  @Override
  public ResponseEntity<List<GenerateCouponResponseDto>> generateCoupons(GenerateCouponRequestDto request) {
    // 쿠폰 생성 count가 0 이하일 경우 BAD_REQUEST 발생
    if (request.getCount() <= 0) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    };

    // 1. CouponTopic을 찾거나 생성
    CouponTopic couponTopic = couponTopicRepository.findByName(request.getTopic())
            .orElseGet(() -> couponTopicRepository.save(new CouponTopic(request.getTopic())));

    // 2. count만큼 쿠폰 생성
    for (int i = 0; i < request.getCount(); i++) {
      String code = RandomUtil.generateRandomString();
      Coupon coupon = new Coupon(code, couponTopic, request.getDescription());
      couponRepository.save(coupon);
    }

    return new ResponseEntity<>(couponRepository.findByTopic(couponTopic), HttpStatus.OK);
  }

  @Transactional
  @Override
  public synchronized ResponseEntity<Void> redeemCoupon(RedeemCouponRequestDto request) {
    // 1. 쿠폰 조회
    Coupon coupon = couponRepository.findByCode(request.getCode())
            .orElseThrow(() -> new IllegalArgumentException("유효하지않은 쿠폰 입니다."));

    // 2. 주제 조회
    Long topicId = coupon.getTopic().getId();
    CouponTopic couponTopic = couponTopicRepository.findCouponTopicById(topicId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주제 입니다."));

    // 3. 유효성 체크
    if (!couponTopic.getIsActive()) {
      throw new IllegalStateException("사용이 정지된 쿠폰 입니다.");
    }

    if (coupon.getIsRedeemed()) {
      throw new IllegalStateException("이미 사용이 완료된 쿠폰입니다.");
    }

    // 4. 사용자 로그 조회
    String userId = request.getUserId();
    couponRedemptionRepository.findByCouponAndUserId(coupon, userId)
            .ifPresent(log -> {
              throw new IllegalStateException("이미 쿠폰을 사용한 유저 입니다.");
            });

    // 5. 쿠폰 사용 처리
    coupon.redeem();
    couponRepository.save(coupon);

    // 6. 사용자 로그 저장
    CouponRedeemLog redemptionLog = new CouponRedeemLog(coupon, userId);
    couponRedemptionRepository.save(redemptionLog);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Transactional
  @Override
  public ResponseEntity<Void> deactivateCoupons(DeactivateCouponRequestDto request) {
    // 1. 주제 조회
    CouponTopic topic = couponTopicRepository.findCouponTopicById(request.getId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주제 입니다."));
    topic.deactivate();

    // 2. 주제 비활성화 처리
    couponTopicRepository.save(topic);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
