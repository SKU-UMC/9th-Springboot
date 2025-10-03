# 1. 홈 화면 미션 API

## API 정보

- **Endpoint**: `/home`
- **Method**: `GET`
- **설명**: 로그인한 사용자의 홈 화면에 보여줄 미션 리스트 조회. JWT 토큰에서 사용자 ID 추출 후 해당 사용자의 미션 데이터를 반환. 페이징 가능.

## Request

### Header

| Key | Value | 설명 |
| --- | --- | --- |
| Authorization | Bearer `<JWT 토큰>` | 로그인 사용자 인증 토큰 |

### Query Parameters

| Key | Type | Required | Default | 설명 |
| --- | --- | --- | --- | --- |
| lastCreateAt | ISO_DATE_TIME | Optional | - | 마지막 조회 미션 생성일 기준으로 페이징 시작 |
| lastMissionId | Long | Optional | - | 마지막 조회 미션 ID 기준으로 페이징 시작 |
| limit | int | Optional | 5 | 한 번에 가져올 미션 개수 |

### Path Variables

- 없음

### Request Body

- 없음

## Response

- **Status**: `200 OK`
- **Content-Type**: `application/json`
- **Body**: JSON 배열 형태, 각 요소(`HomeMissionDTO`) 필드

| Field | Type | 설명 |
| --- | --- | --- |
| regionAddress | String | 사용자 지역 주소 |
| successCount | Long | 사용자가 성공한 미션 수 |
| abstractStoreName | String | 추상 가게 이름 |
| missionId | Long | 미션 ID |
| missionCondition | String | 미션 조건 |
| point | Integer | 미션 포인트 |
| endDate | LocalDate | 미션 종료일 |
| createdAt | LocalDateTime | 미션 생성일 |
| storeName | String | 가게 이름 |
| detailRegion | String | 가게 상세 지역 |

---

# 2. 진행중 미션 조회 API

## API 정보

- **Endpoint**: `/missions/ongoing`
- **Method**: `GET`
- **설명**: 로그인한 사용자의 진행중 미션 목록 조회. JWT 토큰에서 사용자 ID 추출 후 해당 사용자의 진행중 미션 데이터를 반환. 페이징 가능.

## Request

### Header

| Key | Value | 설명 |
| --- | --- | --- |
| Authorization | Bearer `<JWT 토큰>` | 로그인 사용자 인증 토큰 |

### Query Parameters

| Key | Type | Required | Default | 설명 |
| --- | --- | --- | --- | --- |
| lastCreateAt | ISO_DATE_TIME | Optional | - | 마지막 조회 미션 생성일 기준으로 페이징 시작 |
| lastMissionId | Long | Optional | - | 마지막 조회 미션 ID 기준으로 페이징 시작 |

### Path Variables

- 없음

### Request Body

- 없음

## Response

- **Status**: `200 OK`
- **Content-Type**: `application/json`
- **Body**: JSON 배열 형태, 각 요소(`OngoingMissionDTO`) 필드

| Field | Type | 설명 |
| --- | --- | --- |
| missionId | Long | 미션 고유 ID |
| storeName | String | 미션이 속한 가게 이름 |
| point | Integer | 미션 완료 시 지급 포인트 |
| missionCondition | String | 미션 조건 |
| startAt | LocalDateTime | 미션 시작 시간 |

---

# 3. 진행 완료 미션 조회 API

## API 정보

- **Endpoint**: `/missions/complete`
- **Method**: `GET`
- **설명**: 로그인한 사용자의 완료된 미션 목록 조회. JWT 토큰에서 사용자 ID 추출 후 해당 사용자의 완료된 미션 데이터를 반환. 페이징 가능.

## Request

### Header

| Key | Value | 설명 |
| --- | --- | --- |
| Authorization | Bearer `<JWT 토큰>` | 로그인 사용자 인증 토큰 |

### Query Parameters

| Key | Type | Required | Default | 설명 |
| --- | --- | --- | --- | --- |
| lastCreateAt | ISO_DATE_TIME | Optional | - | 마지막 조회 미션 생성일 기준으로 페이징 시작 |
| lastMissionId | Long | Optional | - | 마지막 조회 미션 ID 기준으로 페이징 시작 |
| limit | int | Optional | 5 | 한 번에 가져올 미션 개수 |

### Path Variables

- 없음

### Request Body

- 없음

## Response

- **Status**: `200 OK`
- **Content-Type**: `application/json`
- **Body**: JSON 배열 형태, 각 요소(`MissionDTO`) 필드

| Field | Type | 설명 |
| --- | --- | --- |
| missionId | Long | 미션 고유 ID |
| storeName | String | 미션이 속한 가게 이름 |
| point | Integer | 미션 완료 시 지급 포인트 |
| missionCondition | String | 미션 조건 |
| startAt | LocalDateTime | 미션 시작 시간 |

---

# 4. 미션 성공 처리 API

## API 정보

- **Endpoint**: `/missions/{missionId}/complete`
- **Method**: `POST`
- **설명**: 특정 미션을 성공 처리. JWT 토큰에서 사용자 ID 추출 후 해당 미션의 성공 여부를 `true`로 변경하고, 성공 시간과 포인트를 업데이트.

## Request

### Header

| Key | Value | 설명 |
| --- | --- | --- |
| Authorization | Bearer `<JWT 토큰>` | 로그인 사용자 인증 토큰 |

### Path Variables

| Key | Type | Required | 설명 |
| --- | --- | --- | --- |
| missionId | Long | 필수 | 성공 처리할 미션의 ID |

### Query Parameters

- 없음

### Request Body

- 없음

## Response

- **Status**: `200 OK`
- **Content-Type**: `application/json`
- **Body**: JSON 배열 형태 , 각 요소(`MissionCompleteDTO` )필드

| Field | Type | 설명 |
| --- | --- | --- |
| userId | Long | 미션 성공 처리한 사용자 ID |
| missionId | Long | 성공 처리한 미션 ID |
| updatedPoint | Integer | 성공 처리 후 사용자의 총 포인트 |
| message | String | 성공 처리 메시지 ("미션 성공 처리 완료") |

---

# 5. 리뷰 작성 페이지 데이터 조회 API

## API 정보

- **Endpoint**: `/missions/{missionId}/review/write`
- **Method**: `GET`
- **설명**: 특정 미션을 완료한 유저가 리뷰를 작성할 수 있도록 페이지에 필요한 데이터를 반환. 미션 ID, 가게 ID, 가게 이름 제공.

## Request

### Header

| Key | Value | 설명 |
| --- | --- | --- |
| Authorization | Bearer `<JWT 토큰>` | 로그인 사용자 인증 토큰 |

### Path Variables

| Key | Type | Required | 설명 |
| --- | --- | --- | --- |
| missionId | Long | 필수 | 리뷰를 작성할 미션 ID |

### Query Parameters

- 없음

### Request Body

- 없음

## Response

- **Status**: `200 OK`
- **Content-Type**: `application/json`
- **Body**:JSON 배열 형태 , 각 요소(`ReviewWriteDTO` )필드

**예시 Response**

```json
{
  "missionId": 1,
  "storeId": 10,
  "storeName": "맛집 A"
}

```

---

# 6. 리뷰 등록 API (리뷰 등록 버튼)

## API 정보

- **Endpoint**: `/missions/{missionId}/review`
- **Method**: `POST`
- **설명**: 유저가 작성한 리뷰를 DB에 저장. 리뷰는 완료된 미션에만 작성 가능.

## Request

### Header

| Key | Value | 설명 |
| --- | --- | --- |
| Authorization | Bearer `<JWT 토큰>` | 로그인 사용자 인증 토큰 |

### Path Variables

| Key | Type | Required | 설명 |
| --- | --- | --- | --- |
| missionId | Long | 필수 | 리뷰를 작성할 미션 ID |

### Query Parameters

- 없음

### Request Body

- **Body**:JSON 배열 형태 , 각 요소(`ReviewCreateDTO`)필드

| Field | Type | Required | 설명 |
| --- | --- | --- | --- |
| storeId | Long | 필수 | 리뷰 작성 대상 가게 ID |
| star | Double | 필수 | 별점 (0.0 ~ 5.0) |
| content | String | 필수 | 리뷰 내용 |

**예시 Request**

```json
{
  "storeId": 10,
  "star": 4.5,
  "content": "음식이 정말 맛있었어요!"
}

```

## Response

- **Status**: 200 OK
- **Content-Type**: application/json
- **Body**: JSON 배열 형태 , 각 요소(`ReviewResponseDTO` )필드

| Field | Type | 설명 |
| --- | --- | --- |
| reviewId | Long | 작성된 리뷰 ID |
| message | String | 처리 결과 메시지 |

**예시 Response**

```json
{
  "reviewId": 100,
  "message": "리뷰 작성 완료"
}
```

---

# 7. 회원가입 약관 조회 API

## API 정보

- **Endpoint**: `/signup/terms`
- **Method**: `GET`
- **설명**: 회원가입 시 보여줄 약관 목록 조회. 약관명만 반환.

## Request

### Header

| Key | Value | 설명 |
| --- | --- | --- |
| 없음 | - | 로그인 전 누구나 조회 가능 |

### Query Parameters

| Key | Type | Required | Default | 설명 |
| --- | --- | --- | --- | --- |
| 없음 | - | - | - | 없음 |

### Path Variables

| Key | Type | Required | 설명 |
| --- | --- | --- | --- |
| 없음 | - | - | 없음 |

### Request Body

- 없음

## Response

- **Status**: `200 OK`
- **Content-Type**: `application/json`
- **Body**: JSON 배열 형태, 각 요소(`TermResponseDTO`) 필드

| Field | Type | 설명 |
| --- | --- | --- |
| termName | String | 약관명 |

### 예시 Response

```json
[
  {
    "termName": "서비스 이용약관"
  },
  {
    "termName": "개인정보 처리방침"
  },
  {
    "termName": "마케팅 정보 수신 동의"
  }
]

```

---

# 8. 회원가입 음식 조회 API

## API 정보

- **Endpoint**: `/signup/foods`
- **Method**: `GET`
- **설명**: 회원가입 시 선택할 음식 종류 목록 조회. Enum 이름 반환.

## Request

### Header

| Key | Value | 설명 |
| --- | --- | --- |
| 없음 | - | 로그인 전 누구나 조회 가능 |

### Query Parameters

| Key | Type | Required | Default | 설명 |
| --- | --- | --- | --- | --- |
| 없음 | - | - | - | 없음 |

### Path Variables

| Key | Type | Required | 설명 |
| --- | --- | --- | --- |
| 없음 | - | - | 없음 |

### Request Body

- 없음

## Response

- **Status**: `200 OK`
- **Content-Type**: `application/json`
- **Body**: JSON 배열 형태, 각 요소(`FoodResponseDTO`) 필드

| Field | Type | 설명 |
| --- | --- | --- |
| foodName | String | 음식 이름 |

### 예시 Response

```json
[
  { "foodName": "PIZZA" },
  { "foodName": "SUSHI" },
  { "foodName": "PASTA" }
]

```

---

# 9. 회원가입 완료 API

## API 정보

- **Endpoint**: `/signup`
- **Method**: `POST`
- **설명**: 사용자가 입력한 개인정보, 약관 동의, 선호 음식 정보를 바탕으로 회원가입 완료
    - 지금 구조는 한번에 SignupDTO에 담아 보내는 것이기 때문에 와이어 프레임 보면

      단계별로 페이지가 나눠져있기때문에 클라이언트(프론트엔드)에서 SPA에 저장하는

      것을 전체로 코드를 짰다.


## Request

### Header

| Key | Value | 설명 |
| --- | --- | --- |
| 없음 | - | 로그인 전 누구나 가능 |

### Path Variables

| Key | Type | Required | 설명 |
| --- | --- | --- | --- |
| 없음 | - | - | 없음 |

### Request Body

```json
{
  "name": "홍길동",
  "gender": "M",
  "birth": "1990-01-01",
  "detailAddress": "서울시 강남구",
  "email": "hong@example.com",
  "phoneNumber": "010-1234-5678",
  "locationId": 1,
  "social": "KAKAO",
  "point": 0,
  "termIds": [1,2],
  "foodIds": [1,2]
}

```

| Field | Type | 설명 |
| --- | --- | --- |
| name | String | 사용자 이름 |
| gender | String(enum) | 성별: "M" or "F" |
| birth | LocalDate | 생년월일 |
| detailAddress | String | 상세 주소 |
| email | String | 이메일 |
| phoneNumber | String | 전화번호 |
| locationId | Long | 지역 ID |
| social | String(enum) | 소셜 로그인 종류: "KAKAO", "NAVER", "FACEBOOK" |
| point | Integer | 포인트 (기본 0) |
| termIds | List<Long> | 선택한 약관 ID 리스트 |
| foodIds | List<Long> | 선택한 음식 ID 리스트 |

## Response

- **Status**: `200 OK`
- **Content-Type**: `application/json`
- **Body**: 없음 (혹은 필요 시 메시지 반환 가능)

### 예시 Response

```json
{
  "message": "회원가입 완료"
}

```