package com.wemade.coupon.service;

import com.wemade.coupon.config.DatabaseCleaner;
import com.wemade.coupon.dto.request.DeactivateCouponRequestDto;
import com.wemade.coupon.dto.request.GenerateCouponRequestDto;
import com.wemade.coupon.dto.request.RedeemCouponRequestDto;
import com.wemade.coupon.dto.response.GenerateCouponResponseDto;
import com.wemade.coupon.entity.CouponTopic;
import com.wemade.coupon.errors.errorcode.CustomErrorCode;
import com.wemade.coupon.errors.exception.RestApiException;
import com.wemade.coupon.repository.CouponRepository;
import com.wemade.coupon.repository.CouponTopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CouponServiceImplTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponTopicRepository couponTopicRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void beforeEach() {
        // 데이터 초기화
        databaseCleaner.execute();
    }

    @Test
    @DisplayName("쿠폰 생성 테스트")
    void generateCoupons() {
        // given
        GenerateCouponRequestDto request = new GenerateCouponRequestDto();
        request.setTopic("오픈 이벤트 프로모션");
        request.setCount(10);
        request.setDescription("오픈 이벤트 프로모션으로 발급되는 재화 지급 쿠폰 입니다.");

        // when
        ResponseEntity<List<GenerateCouponResponseDto>> response = couponService.generateCoupons(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(10);

        CouponTopic couponTopic = couponTopicRepository.findByName(request.getTopic()).orElse(null);
        assertThat(couponTopic).isNotNull();
        assertThat(couponRepository.findByTopic(couponTopic).size()).isEqualTo(10);
    }

    @Test
    @DisplayName("쿠폰 생성 테스트 : 쿠폰 설명이 없을 경우")
    void generateCouponsWithNoDescription() {
        // given
        GenerateCouponRequestDto request = new GenerateCouponRequestDto();
        request.setTopic("오픈 이벤트 프로모션");
        request.setCount(10);
        request.setDescription(null);

        // when
        ResponseEntity<List<GenerateCouponResponseDto>> response = couponService.generateCoupons(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(10);
    }

    @Test
    @DisplayName("쿠폰 생성 실패 테스트 : 쿠폰 생성 count가 0 이하 일 경우")
    void generateCouponsFailByCount() {
        // given
        GenerateCouponRequestDto request = new GenerateCouponRequestDto();
        request.setTopic("오픈 이벤트 프로모션");
        request.setCount(0);
        request.setDescription("오픈 이벤트 프로모션으로 발급되는 재화 지급 쿠폰 입니다.");

        // when & then
        assertThatThrownBy(() -> couponService.generateCoupons(request))
                .isInstanceOf(RestApiException.class)
                .hasMessageContaining(CustomErrorCode.INVALID_PARAMETER.getMessage());
    }

    @Test
    @DisplayName("쿠폰 사용 테스트")
    void redeemCoupon() {
        // given
        GenerateCouponRequestDto generateCouponRequestDto = new GenerateCouponRequestDto();
        RedeemCouponRequestDto redeemCouponRequestDto = new RedeemCouponRequestDto();

        // 1. 쿠폰 생성
        generateCouponRequestDto.setTopic("오픈 이벤트 프로모션");
        generateCouponRequestDto.setCount(1);
        generateCouponRequestDto.setDescription("오픈 이벤트 프로모션으로 발급되는 재화 지급 쿠폰 입니다.");

        ResponseEntity<List<GenerateCouponResponseDto>> generateResponse = couponService.generateCoupons(generateCouponRequestDto);

        // 2. 생성된 쿠폰에서 code 추출
        String code = generateResponse.getBody().getFirst().getCode();

        // 3. 생성된 쿠폰 setter
        redeemCouponRequestDto.setCode(code);
        redeemCouponRequestDto.setUserId("testUser");

        // when
        // 4. 생성된 쿠폰 사용
        ResponseEntity<Void> redeemResponse = couponService.redeemCoupon(redeemCouponRequestDto);

        // then
        assertThat(redeemResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("쿠폰 사용 실패 테스트 : 쿠폰이 존재하지 않을 경우")
    void redeemCouponFailByNoCoupon() {
        // given
        RedeemCouponRequestDto redeemCouponRequestDto = new RedeemCouponRequestDto();

        redeemCouponRequestDto.setCode("nocoupon");
        redeemCouponRequestDto.setUserId("testUser");

        // when & then
        assertThatThrownBy(() -> couponService.redeemCoupon(redeemCouponRequestDto))
                .isInstanceOf(RestApiException.class)
                .hasMessageContaining(CustomErrorCode.NOT_FOUND_COUPON.getMessage());
    }

    @Test
    @DisplayName("쿠폰 사용 실패 테스트 : 쿠폰이 비활성화 되어 있을 경우")
    void redeemCouponFailByNotActive() {
        // given
        // 1. 쿠폰 생성
        GenerateCouponRequestDto generateCouponRequestDto = new GenerateCouponRequestDto();
        generateCouponRequestDto.setTopic("오픈 이벤트 프로모션");
        generateCouponRequestDto.setCount(1);
        generateCouponRequestDto.setDescription("오픈 이벤트 프로모션으로 발급되는 재화 지급 쿠폰 입니다.");
        ResponseEntity<List<GenerateCouponResponseDto>> generateResponse = couponService.generateCoupons(generateCouponRequestDto);

        // 2. 생성된 쿠폰에서 topicId & code 추출
        Long topicId = generateResponse.getBody().getFirst().getTopic().getId();
        String code = generateResponse.getBody().getFirst().getCode();

        // 3. 생성된 쿠폰에서 추출된 topicId로 비활성화
        DeactivateCouponRequestDto deactivateCouponRequestDto = new DeactivateCouponRequestDto();
        deactivateCouponRequestDto.setId(topicId);

        couponService.deactivateCoupons(deactivateCouponRequestDto);
        // when
        // 4. 쿠폰 사용 (비활성화된 쿠폰)
        RedeemCouponRequestDto redeemCouponRequestDto = new RedeemCouponRequestDto();
        redeemCouponRequestDto.setUserId("testUser");
        redeemCouponRequestDto.setCode(code);

        // then
        assertThatThrownBy(() -> couponService.redeemCoupon(redeemCouponRequestDto))
                .isInstanceOf(RestApiException.class)
                .hasMessageContaining(CustomErrorCode.DEACTIVATE_COUPON.getMessage());

    }

    @Test
    @DisplayName("쿠폰 사용 실패 테스트 : 이미 쿠폰을 사용한 사용자의 경우")
    void redeemCouponFailByAlreadyRedeemedUser() {
        // given
        // 1. 쿠폰 생성
        GenerateCouponRequestDto generateCouponRequestDto = new GenerateCouponRequestDto();
        generateCouponRequestDto.setTopic("오픈 이벤트 프로모션");
        generateCouponRequestDto.setCount(1);
        generateCouponRequestDto.setDescription("오픈 이벤트 프로모션으로 발급되는 재화 지급 쿠폰 입니다.");
        ResponseEntity<List<GenerateCouponResponseDto>> generateResponse = couponService.generateCoupons(generateCouponRequestDto);

        // when
        // 2. 쿠폰 사용
        RedeemCouponRequestDto redeemCouponRequestDto = new RedeemCouponRequestDto();
        redeemCouponRequestDto.setUserId("testUser");
        redeemCouponRequestDto.setCode(generateResponse.getBody().getFirst().getCode());
        couponService.redeemCoupon(redeemCouponRequestDto);
        // then
        assertThatThrownBy(() -> couponService.redeemCoupon(redeemCouponRequestDto))
                .isInstanceOf(RestApiException.class)
                .hasMessageContaining(CustomErrorCode.ALREADY_REDEEMED_USER.getMessage());
    }

    @Test
    @DisplayName("쿠폰 비활성화 테스트")
    void deactivateCoupons() {
        // given
        // 1. 쿠폰 생성
        GenerateCouponRequestDto generateCouponRequestDto = new GenerateCouponRequestDto();
        generateCouponRequestDto.setTopic("오픈 이벤트 프로모션");
        generateCouponRequestDto.setCount(1);
        generateCouponRequestDto.setDescription("오픈 이벤트 프로모션으로 발급되는 재화 지급 쿠폰 입니다.");
        ResponseEntity<List<GenerateCouponResponseDto>> generateResponse = couponService.generateCoupons(generateCouponRequestDto);
        // when
        // 2. 쿠폰 비활성화
        DeactivateCouponRequestDto deactivateCouponRequestDto = new DeactivateCouponRequestDto();
        deactivateCouponRequestDto.setId(generateResponse.getBody().getFirst().getTopic().getId());
        ResponseEntity<Void> response = couponService.deactivateCoupons(deactivateCouponRequestDto);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}