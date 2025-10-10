# API 명세서

---

# 사용자 API

---

## 회원 가입

### 회원 가입 API 명세

#### Endpoint
```
POST /api/users/signup
```

#### Path Variable
- 없음

#### Query String
- 없음

#### Request Header
```
Content-Type: application/json
```

#### Request Body
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**필드 설명**
- `email` (string, required): 사용자 이메일
- `password` (string, required): 사용자 비밀번호

#### Response Body

**성공 시 (200 OK)**
```json
{
  "id": 1,
  "email": "user@example.com",
  "createdAt": "2025-10-03T10:30:00"
}
```

**필드 설명**
- `id` (number): 사용자 ID
- `email` (string): 사용자 이메일
- `createdAt` (string): 생성 일시

**실패 시 (400 Bad Request)**
```json
"이메일은 필수입니다."
```
또는
```json
"비밀번호는 필수입니다."
```

**실패 시 (500 Internal Server Error)**
```json
"회원 가입 중 오류가 발생했습니다."
```

---

# 미션 API

---

## 사용자의 모든 미션 목록 조회

### 사용자의 모든 미션 목록 조회 API 명세

#### Endpoint
```
GET /api/missions/user/{userId}
```

#### Path Variable
- `userId` (number, required): 사용자 ID

#### Query String
- 없음

#### Request Header
- 없음

#### Request Body
- 없음

#### Response Body

**성공 시 (200 OK)**
```json
[
  {
    "id": 1,
    "title": "첫 미션 완료하기",
    "description": "첫 번째 미션을 완료하세요",
    "rewardPoints": 100,
    "deadline": "2025-10-31T23:59:59",
    "status": "IN_PROGRESS",
    "completedAt": null,
    "createdAt": "2025-10-01T10:00:00"
  }
]
```

**필드 설명**
- `id` (number): 미션 ID
- `title` (string): 미션 제목
- `description` (string): 미션 설명
- `rewardPoints` (number): 보상 포인트
- `deadline` (string): 마감 기한
- `status` (string): 미션 상태 (`IN_PROGRESS`, `COMPLETED`)
- `completedAt` (string|null): 완료 일시
- `createdAt` (string): 생성 일시

**실패 시 (500 Internal Server Error)**
- 빈 응답

---

## 사용자의 진행 중인 미션 목록 조회

### 사용자의 진행 중인 미션 목록 조회 API 명세

#### Endpoint
```
GET /api/missions/user/{userId}/in-progress
```

#### Path Variable
- `userId` (number, required): 사용자 ID

#### Query String
- 없음

#### Request Header
- 없음

#### Request Body
- 없음

#### Response Body

**성공 시 (200 OK)**
```json
[
  {
    "id": 1,
    "title": "첫 미션 완료하기",
    "description": "첫 번째 미션을 완료하세요",
    "rewardPoints": 100,
    "deadline": "2025-10-31T23:59:59",
    "status": "IN_PROGRESS",
    "completedAt": null,
    "createdAt": "2025-10-01T10:00:00"
  }
]
```

**필드 설명**
- 위의 "사용자의 모든 미션 목록 조회"와 동일

**실패 시 (500 Internal Server Error)**
- 빈 응답

---

## 사용자의 완료된 미션 목록 조회

### 사용자의 완료된 미션 목록 조회 API 명세

#### Endpoint
```
GET /api/missions/user/{userId}/completed
```

#### Path Variable
- `userId` (number, required): 사용자 ID

#### Query String
- 없음

#### Request Header
- 없음

#### Request Body
- 없음

#### Response Body

**성공 시 (200 OK)**
```json
[
  {
    "id": 2,
    "title": "두 번째 미션",
    "description": "두 번째 미션을 완료하세요",
    "rewardPoints": 200,
    "deadline": "2025-10-31T23:59:59",
    "status": "COMPLETED",
    "completedAt": "2025-10-03T15:30:00",
    "createdAt": "2025-10-01T10:00:00"
  }
]
```

**필드 설명**
- 위의 "사용자의 모든 미션 목록 조회"와 동일

**실패 시 (500 Internal Server Error)**
- 빈 응답

---

## 미션 완료 처리

### 미션 완료 처리 API 명세

#### Endpoint
```
POST /api/missions/complete
```

#### Path Variable
- 없음

#### Query String
- 없음

#### Request Header
```
Content-Type: application/json
```

#### Request Body
```json
{
  "userId": 1,
  "missionId": 1
}
```

**필드 설명**
- `userId` (number, required): 사용자 ID
- `missionId` (number, required): 미션 ID

#### Response Body

**성공 시 (200 OK)**
```json
{
  "id": 1,
  "title": "첫 미션 완료하기",
  "description": "첫 번째 미션을 완료하세요",
  "rewardPoints": 100,
  "deadline": "2025-10-31T23:59:59",
  "status": "COMPLETED",
  "completedAt": "2025-10-03T15:30:00",
  "createdAt": "2025-10-01T10:00:00"
}
```

**필드 설명**
- 위의 "사용자의 모든 미션 목록 조회"와 동일

**실패 시 (400 Bad Request)**
```json
"사용자 ID는 필수입니다."
```
또는
```json
"미션 ID는 필수입니다."
```

**실패 시 (500 Internal Server Error)**
```json
"미션 완료 처리 중 오류가 발생했습니다."
```

---

## 모든 미션을 사용자에게 할당 (개발/테스트용)

### 모든 미션을 사용자에게 할당 API 명세

#### Endpoint
```
POST /api/missions/assign-all/{userId}
```

#### Path Variable
- `userId` (number, required): 사용자 ID

#### Query String
- 없음

#### Request Header
- 없음

#### Request Body
- 없음

#### Response Body

**성공 시 (200 OK)**
```json
"모든 미션이 사용자에게 할당되었습니다."
```

**실패 시 (400 Bad Request)**
```json
"에러 메시지"
```

**실패 시 (500 Internal Server Error)**
```json
"미션 할당 중 오류가 발생했습니다."
```

---

## 미션 및 사용자 미션 상태 확인 (디버깅용)

### 미션 및 사용자 미션 상태 확인 API 명세

#### Endpoint
```
GET /api/missions/debug/status
```

#### Path Variable
- 없음

#### Query String
- 없음

#### Request Header
- 없음

#### Request Body
- 없음

#### Response Body

**성공 시 (200 OK)**
```json
{
  // 디버그 정보 객체
}
```

---

## 특정 사용자의 모든 데이터 확인 (디버깅용)

### 특정 사용자의 모든 데이터 확인 API 명세

#### Endpoint
```
GET /api/missions/debug/user/{userId}
```

#### Path Variable
- `userId` (number, required): 사용자 ID

#### Query String
- 없음

#### Request Header
- 없음

#### Request Body
- 없음

#### Response Body

**성공 시 (200 OK)**
```json
{
  // 사용자 디버그 정보 객체
}
```

---

# 미션 리뷰 API

---

## 리뷰 작성

### 리뷰 작성 API 명세

#### Endpoint
```
POST /api/reviews
```

#### Path Variable
- 없음

#### Query String
- 없음

#### Request Header
```
Content-Type: application/json
```

#### Request Body
```json
{
  "userId": 1,
  "missionId": 1,
  "rating": 5,
  "content": "정말 좋은 미션이었습니다!"
}
```

**필드 설명**
- `userId` (number, required): 사용자 ID
- `missionId` (number, required): 미션 ID
- `rating` (number, required): 평점 (1-5)
- `content` (string, required): 리뷰 내용

#### Response Body

**성공 시 (200 OK)**
```json
{
  "id": 1,
  "missionId": 1,
  "missionTitle": "첫 미션 완료하기",
  "userId": 1,
  "userEmail": "user@example.com",
  "rating": 5,
  "content": "정말 좋은 미션이었습니다!",
  "createdAt": "2025-10-03T16:00:00",
  "updatedAt": "2025-10-03T16:00:00"
}
```

**필드 설명**
- `id` (number): 리뷰 ID
- `missionId` (number): 미션 ID
- `missionTitle` (string): 미션 제목
- `userId` (number): 사용자 ID
- `userEmail` (string): 사용자 이메일
- `rating` (number): 평점 (1-5)
- `content` (string): 리뷰 내용
- `createdAt` (string): 생성 일시
- `updatedAt` (string): 수정 일시

**실패 시 (400 Bad Request)**
```json
"사용자 ID는 필수입니다."
```
또는
```json
"미션 ID는 필수입니다."
```
또는
```json
"평점은 필수입니다."
```
또는
```json
"리뷰 내용은 필수입니다."
```

**실패 시 (500 Internal Server Error)**
```json
"리뷰 작성 중 오류가 발생했습니다."
```

---

## 리뷰 수정

### 리뷰 수정 API 명세

#### Endpoint
```
PUT /api/reviews/{reviewId}
```

#### Path Variable
- `reviewId` (number, required): 리뷰 ID

#### Query String
- 없음

#### Request Header
```
Content-Type: application/json
```

#### Request Body
```json
{
  "userId": 1,
  "rating": 4,
  "content": "수정된 리뷰 내용입니다."
}
```

**필드 설명**
- `userId` (number, required): 사용자 ID
- `rating` (number, required): 평점 (1-5)
- `content` (string, required): 리뷰 내용

#### Response Body

**성공 시 (200 OK)**
```json
{
  "id": 1,
  "missionId": 1,
  "missionTitle": "첫 미션 완료하기",
  "userId": 1,
  "userEmail": "user@example.com",
  "rating": 4,
  "content": "수정된 리뷰 내용입니다.",
  "createdAt": "2025-10-03T16:00:00",
  "updatedAt": "2025-10-03T16:30:00"
}
```

**필드 설명**
- 위의 "리뷰 작성"과 동일

**실패 시 (400 Bad Request)**
```json
"사용자 ID는 필수입니다."
```
또는
```json
"평점은 필수입니다."
```
또는
```json
"리뷰 내용은 필수입니다."
```

**실패 시 (500 Internal Server Error)**
```json
"리뷰 수정 중 오류가 발생했습니다."
```

---

## 사용자의 모든 리뷰 조회

### 사용자의 모든 리뷰 조회 API 명세

#### Endpoint
```
GET /api/reviews/user/{userId}
```

#### Path Variable
- `userId` (number, required): 사용자 ID

#### Query String
- 없음

#### Request Header
- 없음

#### Request Body
- 없음

#### Response Body

**성공 시 (200 OK)**
```json
[
  {
    "id": 1,
    "missionId": 1,
    "missionTitle": "첫 미션 완료하기",
    "userId": 1,
    "userEmail": "user@example.com",
    "rating": 5,
    "content": "정말 좋은 미션이었습니다!",
    "createdAt": "2025-10-03T16:00:00",
    "updatedAt": "2025-10-03T16:00:00"
  }
]
```

**필드 설명**
- 위의 "리뷰 작성"과 동일

**실패 시 (500 Internal Server Error)**
- 빈 응답

---

## 특정 미션의 모든 리뷰 조회

### 특정 미션의 모든 리뷰 조회 API 명세

#### Endpoint
```
GET /api/reviews/mission/{missionId}
```

#### Path Variable
- `missionId` (number, required): 미션 ID

#### Query String
- 없음

#### Request Header
- 없음

#### Request Body
- 없음

#### Response Body

**성공 시 (200 OK)**
```json
[
  {
    "id": 1,
    "missionId": 1,
    "missionTitle": "첫 미션 완료하기",
    "userId": 1,
    "userEmail": "user@example.com",
    "rating": 5,
    "content": "정말 좋은 미션이었습니다!",
    "createdAt": "2025-10-03T16:00:00",
    "updatedAt": "2025-10-03T16:00:00"
  }
]
```

**필드 설명**
- 위의 "리뷰 작성"과 동일

**실패 시 (500 Internal Server Error)**
- 빈 응답

---

## 리뷰 삭제

### 리뷰 삭제 API 명세

#### Endpoint
```
DELETE /api/reviews/{reviewId}
```

#### Path Variable
- `reviewId` (number, required): 리뷰 ID

#### Query String
- `userId` (number, required): 사용자 ID

#### Request Header
- 없음

#### Request Body
- 없음

#### Response Body

**성공 시 (200 OK)**
```json
"리뷰가 삭제되었습니다."
```

**실패 시 (400 Bad Request)**
```json
"에러 메시지"
```

**실패 시 (500 Internal Server Error)**
```json
"리뷰 삭제 중 오류가 발생했습니다."
```

---

## 공통 응답 상태 코드

| 상태 코드 | 설명 |
|----------|------|
| 200 OK | 요청 성공 |
| 400 Bad Request | 잘못된 요청 (필수 파라미터 누락, 유효성 검증 실패 등) |
| 500 Internal Server Error | 서버 내부 오류 |