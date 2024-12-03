##### **Overview**
> 쿠폰 생성
---
#### **URI**
Method : POST <br/>
Endpoint : /api/coupon/generate <br/>
<strong>ex) {{API_URL}}/api/coupon/generate</strong>

##### **Request Body**
```json
{
  "topic": "오픈 이벤트 프로모션",
  "count":4,
  "description": "오픈 이벤트 프로모션으로 발급되는 재화 지급 쿠폰 입니다."
}
```

##### **Request Body Type**
| Key         |   Type   | Required   | Description |
|-------------|:--------:|:-----------|-------------|
| topic       | `string` | `yes`      | 쿠폰 주제명      |
| count       |  `int`   | `yes`      | 쿠폰 수량       |
| description | `string` | `optional` | 쿠폰 내용       |

---
##### **Response**
```json
[
  {
    "id": 1,
    "code": "L2nnMliFEJyHGpVc",
    "topic": {
      "id": 1,
      "name": "오픈 이벤트 프로모션",
      "createdAt": "2024-12-03T02:34:57.217674",
      "isActive": true
    },
    "description": "오픈 이벤트 프로모션으로 발급되는 재화 지급 쿠폰 입니다.",
    "createdAt": "2024-12-03T02:34:57.238895",
    "updatedAt": "2024-12-03T02:34:57.238902"
  },
  {
    "id": 2,
    "code": "hpYMVdOqN7IpoQGX",
    "topic": {
      "id": 1,
      "name": "오픈 이벤트 프로모션",
      "createdAt": "2024-12-03T02:34:57.217674",
      "isActive": true
    },
    "description": "오픈 이벤트 프로모션으로 발급되는 재화 지급 쿠폰 입니다.",
    "createdAt": "2024-12-03T02:34:57.243628",
    "updatedAt": "2024-12-03T02:34:57.243633"
  },
  {
    "id": 3,
    "code": "WQ1ASj21vcy8oe6a",
    "topic": {
      "id": 1,
      "name": "오픈 이벤트 프로모션",
      "createdAt": "2024-12-03T02:34:57.217674",
      "isActive": true
    },
    "description": "오픈 이벤트 프로모션으로 발급되는 재화 지급 쿠폰 입니다.",
    "createdAt": "2024-12-03T02:34:57.246241",
    "updatedAt": "2024-12-03T02:34:57.246247"
  },
  {
    "id": 4,
    "code": "oBHnrgoU3DRHwQ7x",
    "topic": {
      "id": 1,
      "name": "오픈 이벤트 프로모션",
      "createdAt": "2024-12-03T02:34:57.217674",
      "isActive": true
    },
    "description": "오픈 이벤트 프로모션으로 발급되는 재화 지급 쿠폰 입니다.",
    "createdAt": "2024-12-03T02:34:57.249476",
    "updatedAt": "2024-12-03T02:34:57.249481"
  }
]
```
##### **Response Type**
| Key             |      Type       |  Required  | Description  |
|-----------------|:---------------:|:----------:|--------------|
| id              |      `int`      |   `yes`    | 쿠폰 ID        |
| code            |    `string`     |   `yes`    | 쿠폰 CODE      |
| topic           |    `object`     |   `yes`    | 쿠폰 주제        |
| topic.id        |      `int`      |   `yes`    | 쿠폰 주제 ID     |
| topic.name      |    `string`     |   `yes`    | 쿠폰 주제 이름     |
| topic.createdAt | `LocalDateTime` |   `yes`    | 쿠폰 주제 생성일    |
| topic.isActive  |    `Boolean`    |   `yes`    | 쿠폰 주제 활성화 여부 |
| description     |    `String`     | `optional` | 쿠폰 설명        |
| createdAt       | `LocalDateTime` |   `yes`    | 쿠폰 생성일       |
| updateAt        | `LocalDateTime` |   `yes`    | 쿠폰 내용 수정일    |