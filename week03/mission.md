# 미션

## **홈 화면**

- **API Endpoint**: `GET /missions`
- **Request Body: X**
- **Request Header: X**
- **Query String**: `?location="안암동"`

---

## **마이 페이지 리뷰 작성**

- **API Endpoint:** `POST /review`
- **Request Body:**
    
    ```json
    {
    	star: 5,
    	content: "리뷰 내용",
    	image: image.jpg
    }
    ```
    
- **Request Header:**
    
    ```json
    {
    	Content-Type: multipart/form-data,
    	Authorization : Bearer {accessToken}
    }
    ```
    
- **Query String: X**

---

## **미션 목록 조회(진행중, 진행 완료)**

- **API Endpoint:** `GET /users/{userId}/missions`
- **Request Body: X**
- **Request Header**

```json
{
	Authorization : Bearer {accessToken}
}
```

- **Query String: X**

---

## **미션 성공 누르기**

- **API Endpoint:** `POST /users/{userId}/missions/{missionId}/success`
- **Request Body: X**
- **Request Header:**

```json
{
	Content-Type: application/json,
	Authorization : Bearer {accessToken}
}
```

- **Query String: X**

---

## **회원 가입 하기**

- **API Endpoint:** `POST /auth/users/sign-up`
- **Request Body:**

```json
{
	email: "abc@abc.com",
	password: "1234",
	name: "홍길동",
	gender: "MALE",
	birth: "2000-01-01",
	address: "서울특별시 ... 101호",
	phoneNum: "010-0000-0000",
	food: "한식"
}
```

- **Request Header:**

```json
{
	Content-Type: application/json
}
```

- **Query String: X**

---