## /api/v1/point-events

### method: GET
### 설명: 전체 회원들의 포인트 부여 히스토리 조회
### Input Parameter
| parameter name | type | required | description |
|----------------|------|----------|-------------|
|                |      |          |             |

### Response
| parameter name | type      | description     |
|----------------|-----------|-----------------|
| reviewId       | [].string | 리뷰 ID           |
| userId         | [].string | 유저 ID           |
| actionType     | [].string | 변동 사유(등록,수정,삭제) |
| amount         | [].number | 변동량             |