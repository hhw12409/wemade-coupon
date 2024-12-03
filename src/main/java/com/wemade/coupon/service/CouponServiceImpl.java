package com.wemade.coupon.service;

import com.wemade.coupon.dto.request.DeactivateCouponRequestDto;
import com.wemade.coupon.dto.request.GenerateCouponRequestDto;
import com.wemade.coupon.dto.request.RedeemCouponRequestDto;
import com.wemade.coupon.dto.response.GenerateCouponResponseDto;
import com.wemade.coupon.entity.Coupon;
import com.wemade.coupon.entity.CouponRedeemLog;
import com.wemade.coupon.entity.CouponTopic;
import com.wemade.coupon.errors.errorcode.CustomErrorCode;
import com.wemade.coupon.errors.exception.RestApiException;
import com.wemade.coupon.repository.CouponRedeemLogRepository;
import com.wemade.coupon.repository.CouponRepository;
import com.wemade.coupon.repository.CouponTopicRepository;
import com.wemade.coupon.utils.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
  private final CouponRepository couponRepository;
  private final CouponTopicRepository couponTopicRepository;
  private final CouponRedeemLogRepository couponRedemptionRepository;

  @Transactional
  @Override
  public ResponseEntity<List<GenerateCouponResponseDto>> generateCoupons(GenerateCouponRequestDto request) {
    // 1. 쿠폰 생성 count가 0 이하일 경우 Error발생
    if (request.getCount() <= 0) {
      throw new RestApiException(CustomErrorCode.INVALID_PARAMETER);
    };

    // 2. CouponTopic을 찾거나 생성
    CouponTopic couponTopic = couponTopicRepository.findByName(request.getTopic())
            .orElseGet(() -> couponTopicRepository.save(new CouponTopic(request.getTopic())));

    // 3. count만큼 쿠폰 생성
    for (int i = 0; i < request.getCount(); i++) {
      String code = RandomUtil.generateRandomString();
      Coupon coupon = new Coupon(code, couponTopic, request.getDescription());
      couponRepository.save(coupon);
    }

    // 4. Topic에 해당하는 쿠폰 조회
    List<Coupon> coupons = couponRepository.findByTopic(couponTopic);

    // 5. Response 생성 (GenerateCouponResponseDto)
    List<GenerateCouponResponseDto> response = coupons.stream()
            .map(GenerateCouponResponseDto::new)
            .toList();

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @Transactional
  @Override
  public synchronized ResponseEntity<Void> redeemCoupon(RedeemCouponRequestDto request) {
    // 1. 쿠폰 조회
    Coupon coupon = couponRepository.findByCode(request.getCode())
            .orElseThrow(() -> new RestApiException(CustomErrorCode.NOT_FOUND_COUPON));

    // 2. 주제 조회
    Long topicId = coupon.getTopic().getId();
    CouponTopic couponTopic = couponTopicRepository.findCouponTopicById(topicId)
            .orElseThrow(() -> new RestApiException(CustomErrorCode.NOT_FOUND_TOPIC_COUPON));

    // 3. 유효성 체크
    if (!couponTopic.getIsActive()) {
      throw new RestApiException(CustomErrorCode.DEACTIVATE_COUPON);
    }

    // 4. 사용자 로그 조회
    String userId = request.getUserId();
    couponRedemptionRepository.findByCouponAndUserId(coupon, userId)
            .ifPresent(log -> {
              throw new RestApiException(CustomErrorCode.ALREADY_REDEEMED_USER);
            });

    // 5. 사용자 로그 저장
    CouponRedeemLog redemptionLog = new CouponRedeemLog(coupon, userId);
    couponRedemptionRepository.save(redemptionLog);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Transactional
  @Override
  public ResponseEntity<Void> deactivateCoupons(DeactivateCouponRequestDto request) {
    // 1. 주제 조회
    CouponTopic topic = couponTopicRepository.findCouponTopicById(request.getId())
            .orElseThrow(() -> new RestApiException(CustomErrorCode.NOT_FOUND_TOPIC_COUPON));
    topic.deactivate();

    // 2. 주제 비활성화 처리
    couponTopicRepository.save(topic);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
