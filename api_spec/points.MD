## /api/v1/users/points

### method: GET
### 설명: 개인의 누적 포인트 조회
### Input Parameter
| parameter name | type          | required | description |
|----------------|---------------|----------|-------------|
| user_id        | string        | Y        | 유저 ID       |

### Response
| parameter name | type   | description |
|----------------|--------|-------------|
| amount         | number | 누적 포인트량     |