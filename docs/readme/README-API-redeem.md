##### **Overview**
> 쿠폰 사용
---
#### **URI**
Method : POST <br/>
Endpoint : /api/coupon/redeem <br/>
<strong>ex) {{API_URL}}/api/coupon/redeem</strong>

##### **Request Body**
```json
{
  "code": "1eftHjRheCKMSeMT",
  "userId": "hwanghw1114"
}
```

##### **Request Body Type**
| Key    |   Type   | Required | Description |
|--------|:--------:|:---------|-------------|
| code   | `string` | `yes`    | 쿠폰 코드       |
| userId | `string` | `yes`    | 유저 ID       |

---
##### **Response**
```json

```
##### **Response Type**
| Key             |      Type       | Required | Description  |
|-----------------|:---------------:|:--------:|--------------|
