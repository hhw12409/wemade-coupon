# Coupon Service API

## 주요 기능
1. 쿠폰 발행
2. 쿠폰 사용 (사용자별 1회 사용)
3. 쿠폰 정지 (주제별)

## 실행 방법
1. 프로젝트 클론
2. MySQL 설정
3. `application.properties`에 DB 정보 입력
4. Gradle 빌드: `./gradlew bootRun`
5. Swagger로 API 테스트 가능.

## API Endpoints
### 1. 쿠폰 발행
- **POST** `/api/coupon/generate`

### 2. 쿠폰 사용
- **POST** `/api/coupon/redeem`

### 3. 쿠폰 정지
- **POST** `/api/coupon/deactivate`
