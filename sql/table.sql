-- 쿠폰 주제 테이블
CREATE TABLE coupon_topic (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 쿠폰 테이블
CREATE TABLE coupon (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(16) NOT NULL UNIQUE,
    topic_id BIGINT NOT NULL,
    -- TODO : is_redeemed가 아니라 단일 쿠폰인가에 대한 컬럼 추가
    is_redeemed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (topic_id) REFERENCES coupon_topic(id)
);

-- 쿠폰 사용 로그
CREATE TABLE coupon_redeem_log (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id   BIGINT      NOT NULL,
    user_id     VARCHAR(20) NOT NULL,
    redeemed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (coupon_id) REFERENCES coupon (id),
    UNIQUE (coupon_id, user_id) -- 동일 사용자가 같은 쿠폰을 중복 사용하지 못하도록 제한
);

CREATE INDEX idx_coupon_topic_id ON coupon (topic_id);
