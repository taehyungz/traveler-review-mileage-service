# traveler-review-mileage-service
사용자들이 장소에 리뷰를 작성할 때 포인트를 부여하고, 전체/개인에 대한 포인트 부여 및 히스토리와 개인별 누적 포인트 관리하는 서비스


## 실행 방법
### 프로젝트 클론
> cd ~

> git clone https://github.com/taehyungz/traveler-review-mileage-service.git

### 프로젝트로 이동
> cd ./traveler-review-mileage-service
  
### 도커 컴포즈 실행
> cd ./docker-compose
  
> docker-compose -p mileage up -d
  
### 스프링 부트 프로젝트 실행  
프로젝트 빌드
> cd ../

> ./gradlew bootJar

프로젝트 실행
> cd ./build/libs

> java -jar build/libs/mileage-0.0.1-SNAPSHOT.jar


## 프로젝트 설명
### 기능
이벤트 발생을 구독하여 포인트 관련 서비스 기능을 제공합니다.  
- 포인트 적립 API [POST /events](https://github.com/taehyungz/traveler-review-mileage-service/blob/main/api_spec/events.MD)
- 포인트 조회 API
  - 전체 회원의 포인트 부여 히스토리 조회 [GET /api/v1/points](https://github.com/taehyungz/traveler-review-mileage-service/blob/main/api_spec/users_points.MD)
  - 개인의 누적 포인트 조회 [GET /api/v1/users/points](https://github.com/taehyungz/traveler-review-mileage-service/blob/main/api_spec/points.MD)
  - 개인의 누적 포인트와 포인트 부여 히스토리 조회 [GET /api/v1/users/point-events](https://github.com/taehyungz/traveler-review-mileage-service/blob/main/api_spec/users_point_events.MD)

### 스키마
[최종 스키마 sql](https://github.com/taehyungz/traveler-review-mileage-service/blob/main/schema.sql)