# Coupon Service API

## 주요 기능
1. 쿠폰 발행
2. 쿠폰 사용 (사용자별 1회 사용)
3. 쿠폰 정지 (주제별)

## 요구사항
1. 1개의 주제당 n개의 쿠폰 생성
2. 쿠폰코드는 사용자별 1회 사용이 가능
3. 쿠폰코드는 숫자와 알파벳 혼용 16자리
4. 동시 요청시 중복 사용이 되지 않도록 처리

## 실행 방법
1. 프로젝트 클론
2. MySQL 설정
3. `application.yaml`에 DB 정보 입력
4. Gradle 빌드: `./gradlew bootRun`
5. Swagger로 API 테스트 가능. (http://localhost:8081/swagger-ui/index.html#/)
  

## API Endpoints
### 1. 쿠폰 발행
- **POST** `/api/coupon/generate`

### 2. 쿠폰 사용
- **POST** `/api/coupon/redeem`

### 3. 쿠폰 정지
- **POST** `/api/coupon/deactivate`


# Project Documentation
- API 명세서
  - [쿠폰 발행](./docs/readme/README-API-generate.md)
  - [쿠폰 사용](./docs/readme/README-API-redeem.md)
  - [쿠폰 정지](./docs/readme/README-API-deactivate.md)

- Database Schema
  - [Database Schema](./docs/sql/coupon.sql)
