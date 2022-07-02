## /api/v1/users/point-events

### method: GET
### 설명: 개인의 포인트 부여 히스토리 조회
### Input Parameter
| parameter name | type          | required | description  |
|----------------|---------------|----------|--------------|
| user_id        | string        | Y        | 유저 ID        |

### Response
| parameter name                | type      | description     |
|-------------------------------|-----------|-----------------|
| amount                        | number    | 누적 포인트량         |
| pointEventInfoList.reviewId   | [].string | 적립 리뷰 ID        |
| pointEventInfoList.actionType | [].string | 적립 사유(등록,수정,삭제) |
| pointEventInfoList.amount     | [].number | 적립량             |