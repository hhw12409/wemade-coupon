package com.wemade.coupon.errors.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode {
    // COMMON
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 파라미터가 전달되었습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에러"),

    // COUPON
    NOT_FOUND_TOPIC_COUPON(HttpStatus.BAD_REQUEST, "쿠폰 주제를 찾을 수 없습니다."),
    NOT_FOUND_COUPON(HttpStatus.BAD_REQUEST, "쿠폰을 찾을 수 없습니다."),
    DEACTIVATE_COUPON(HttpStatus.BAD_REQUEST, "사용이 정지된 쿠폰 입니다."),
    ALREADY_REDEEMED_USER(HttpStatus.BAD_REQUEST, "이미 쿠폰을 사용한 유저 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
