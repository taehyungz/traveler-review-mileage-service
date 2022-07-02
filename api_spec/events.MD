## /events

### method: POST
### 설명: 리뷰 작성/수정/삭제 이벤트를 통한 포인트 적립 API
### Input Parameter
| parameter name   | type     | required | description         |
|------------------|----------|----------|---------------------|
| type             | string   | Y        | 이벤트 타입              |
| action           | string   | Y        | 리뷰 이벤트 행위(등록,수정,삭제) |
| reviewId         | string   | Y        | 리뷰 ID               |
| content          | string   | Y        | 리뷰 내용               |
| attachedPhotoIds | string[] |          | 첨부된 사진 id           |
| userId           | string   | Y        | 유저 ID               |
| placeId          | string   | Y        | 장소 ID               |

### Response
| parameter name | type | description |
|----------------|------|-------------|
|                |      |             |