package com.wemade.coupon.service;

import com.wemade.coupon.repository.CouponRedeemLogRepository;
import com.wemade.coupon.repository.CouponRepository;
import com.wemade.coupon.repository.CouponTopicRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CouponServiceImplTest {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponTopicRepository couponTopicRepository;

    @Autowired
    private CouponRedeemLogRepository couponRedemptionRepository;

    @Test
    @DisplayName("쿠폰 생성 테스트")
    void generateCoupons() {
        // given
        // when
        // then
    }

    @Test
    @DisplayName("쿠폰 생성 실패 테스트 : 쿠폰 생성 count가 0 이하 일 경우")
    void generateCouponsFailByCount() {
        // given
        // when
        // then
    }

    @Test
    @DisplayName("쿠폰 사용 테스트")
    void redeemCoupon() {
        // given
        // when
        // then
    }

    @Test
    @DisplayName("쿠폰 사용 실패 테스트 : 쿠폰이 존재하지 않을 경우")
    void redeemCouponFailByNoCoupon() {
        // given
        // when
        // then
    }

    @Test
    @DisplayName("쿠폰 사용 실패 테스트 : 쿠폰 주제가 존재하지 않을 경우")
    void redeemCouponFailByNoTopic() {
        // given
        // when
        // then
    }

    @Test
    @DisplayName("쿠폰 사용 실패 테스트 : 쿠폰이 비활성화 되어 있을 경우")
    void redeemCouponFailByNotActive() {
        // given
        // when
        // then
    }

    @Test
    @DisplayName("쿠폰 사용 실패 테스트 : 이미 쿠폰을 사용한 사용자의 경우")
    void redeemCouponFailByAlreadyRedeemedUser() {
        // given
        // when
        // then
    }

    @Test
    @DisplayName("쿠폰 비활성화 테스트")
    void deactivateCoupons() {
        // given
        // when
        // then
    }

    @Test
    @DisplayName("쿠폰 비활성화 실패 테스트 : 쿠폰 주제가 존재하지 않을 경우")
    void deactivateCouponsFailByNoTopic() {
        // given
        // when
        // then
    }
}