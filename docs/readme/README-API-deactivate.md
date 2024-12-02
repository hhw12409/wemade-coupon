##### **Overview**
> 나의 이벤트 참여 정보 조회
---
#### **URI**
Method : POST <br/>
Endpoint : /event/enchant/user/info <br/>
<strong>ex) {{API_URL}}/event/enchant/user/info</strong>

---
##### **API Calling System**
1. `MIR2/3 WEB` > `API`

---

##### **Request Body**
```json
{
  "gameCode": 3,
  "eventCode": 20241218,
  "auth": {
    "cusrVal": "A=91G6PvL1hPYeRuYHoK6xd0hN&F=91G6PvL1hPYeRuYHoK6xd0hN&M=PXx2BgDuJ8PKMgcL1G5g92vF&M2I...."
  }
}
```

##### **Request Body Type**
| Key          |   Type   | Required |  Value  | Description     |
|--------------|:--------:|:--------:|:-------:|-----------------|
| gameCode     |  `int`   |  `yes`   | `2` `3` | `2` 미르2 `3` 미르3 |
| eventCode    |  `int`   |  `yes`   |         | 이벤트코드           |
| auth         | `object` |  `yes`   |         | 인증정보            |
| auth.cusrVal | `string` |  `yes`   |         | 로그인 쿠키값         |

---
##### **Response**
```json
{
  "code": "200",
  "userInfo": {
    "rank": 1,
    "gameLevel": "0",
    "percentage": "100.00",
    "currentDate": "2024.12.18 14:59:17",
    "modiDate": "2024.03.18 18:27:17"
  }
}
```
##### **Response Type**
| Key                  |      Type       |  Required  | Description                                                                                |
|----------------------|:---------------:|:----------:|--------------------------------------------------------------------------------------------|
| code                 |    `string`     |   `yes`    | `200` 정상<br/>`M20001` 잘못된 요청입니다.<br/>`M20002` 인증에 실패하였습니다.<br/>`M20004` 현재 진행중인 이벤트가 아닙니다. |
| errorMessage         |    `string`     | `optional` | 에러 메시지                                                                                     |
| userInfo             |    `object`     |   `yes`    | 이벤트 참여 정보                                                                                  |
| userInfo.rank        |      `int`      | `optional` | 랭킹 순위                                                                                      |
| userInfo.gameLevel   |    `string`     |   `yes`    | 현재 강화레벨                                                                                    |
| userInfo.percentage  |    `string`     |   `yes`    | 강화게이지 (소수 둘째자리까지)                                                                          |
| userInfo.currentDate | `LocalDateTime` |   `yes`    | 업데이트 날짜 (최신 업데이트)                                                                          |
| userInfo.modiDate    | `LocalDateTime` | `optional` | 수정일 (달성 일자)       