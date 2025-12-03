# 소셜로그인, RefreshToken 구현

# 카카오 소셜로그인 구현

---

# 순서

1. Kakao Developer 앱 생성
2. Redirect URI 설정, REST API 키 확인
3. [https://kauth.kakao.com/oauth/authorize?client_id={REST_API_KEY}&redirect_uri={REDIRECT_URI}&response_type=code](https://kauth.kakao.com/oauth/authorize?client_id=%7BREST_API_KEY%7D&redirect_uri=%7BREDIRECT_URI%7D&response_type=code)
    
    해당 URL로 code 데이터를 받아온다.
    
4. 받아온 code로 카카오 서버의 AccessToken과 RefreshToken을 받아와야 된다.
    
    ```
    요청 URL:
    	https://kauth.kakao.com/oauth/token
    
    Method:
    	POST
    
    Header: 
    	Content-type: application/x-www-form-urlencoded;charset=utf-8
    
    Body (Parameter):
    	grant_type: authorization_code (고정)
    	client_id: 앱 REST API 키
    	redirect_uri: 설정한 Redirect URI
    	code: 프론트에서 받은 인가 코드
    ```
    
    [https://kauth.kakao.com/oauth/token](https://kauth.kakao.com/oauth/token) 이 URL로 필요 정보들을 담아서 POST 요청을 보내면 카카오에서 AccessToken과 RefreshToken을 발급해준다.
    
5. 발급받은 AccessToken으로 사용자 정보를 가져온다.
    
    ```
    요청 URL:
    	https://kapi.kakao.com/v2/user/me
    
    Method: 
    	GET 또는 POST
    
    Header:
    	Authorization: Bearer {ACCESS_TOKEN}
    	Content-type: application/x-www-form-urlencoded;charset=utf-8
    ```
    
    [https://kapi.kakao.com/v2/user/me](https://kapi.kakao.com/v2/user/me) 이 URL로 카카오에서 발급받은 AccessToken을 담아 GET/POST 요청을 보내면 사용자의 정보를 받아올 수 있다. (kakaoId(식별자), email 등)
    
    - 가져올 수 있는 정보 DTO
        
        ```java
        @Getter
        @NoArgsConstructor //역직렬화를 위한 기본 생성자
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class KakaoUserInfoResponseDto {
        
            //회원 번호
            @JsonProperty("id")
            public Long id;
        
            //자동 연결 설정을 비활성화한 경우만 존재.
            //true : 연결 상태, false : 연결 대기 상태
            @JsonProperty("has_signed_up")
            public Boolean hasSignedUp;
        
            //서비스에 연결 완료된 시각. UTC
            @JsonProperty("connected_at")
            public Date connectedAt;
        
            //카카오싱크 간편가입을 통해 로그인한 시각. UTC
            @JsonProperty("synched_at")
            public Date synchedAt;
        
            //사용자 프로퍼티
            @JsonProperty("properties")
            public HashMap<String, String> properties;
        
            //카카오 계정 정보
            @JsonProperty("kakao_account")
            public KakaoAccount kakaoAccount;
        
            //uuid 등 추가 정보
            @JsonProperty("for_partner")
            public Partner partner;
        
            @Getter
            @NoArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            public class KakaoAccount {
        
                //프로필 정보 제공 동의 여부
                @JsonProperty("profile_needs_agreement")
                public Boolean isProfileAgree;
        
                //닉네임 제공 동의 여부
                @JsonProperty("profile_nickname_needs_agreement")
                public Boolean isNickNameAgree;
        
                //프로필 사진 제공 동의 여부
                @JsonProperty("profile_image_needs_agreement")
                public Boolean isProfileImageAgree;
        
                //사용자 프로필 정보
                @JsonProperty("profile")
                public Profile profile;
        
                //이름 제공 동의 여부
                @JsonProperty("name_needs_agreement")
                public Boolean isNameAgree;
        
                //카카오계정 이름
                @JsonProperty("name")
                public String name;
        
                //이메일 제공 동의 여부
                @JsonProperty("email_needs_agreement")
                public Boolean isEmailAgree;
        
                //이메일이 유효 여부
                // true : 유효한 이메일, false : 이메일이 다른 카카오 계정에 사용돼 만료
                @JsonProperty("is_email_valid")
                public Boolean isEmailValid;
        
                //이메일이 인증 여부
                //true : 인증된 이메일, false : 인증되지 않은 이메일
                @JsonProperty("is_email_verified")
                public Boolean isEmailVerified;
        
                //카카오계정 대표 이메일
                @JsonProperty("email")
                public String email;
        
                //연령대 제공 동의 여부
                @JsonProperty("age_range_needs_agreement")
                public Boolean isAgeAgree;
        
                //연령대
                //참고 https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
                @JsonProperty("age_range")
                public String ageRange;
        
                //출생 연도 제공 동의 여부
                @JsonProperty("birthyear_needs_agreement")
                public Boolean isBirthYearAgree;
        
                //출생 연도 (YYYY 형식)
                @JsonProperty("birthyear")
                public String birthYear;
        
                //생일 제공 동의 여부
                @JsonProperty("birthday_needs_agreement")
                public Boolean isBirthDayAgree;
        
                //생일 (MMDD 형식)
                @JsonProperty("birthday")
                public String birthDay;
        
                //생일 타입
                // SOLAR(양력) 혹은 LUNAR(음력)
                @JsonProperty("birthday_type")
                public String birthDayType;
        
                //성별 제공 동의 여부
                @JsonProperty("gender_needs_agreement")
                public Boolean isGenderAgree;
        
                //성별
                @JsonProperty("gender")
                public String gender;
        
                //전화번호 제공 동의 여부
                @JsonProperty("phone_number_needs_agreement")
                public Boolean isPhoneNumberAgree;
        
                //전화번호
                //국내 번호인 경우 +82 00-0000-0000 형식
                @JsonProperty("phone_number")
                public String phoneNumber;
        
                //CI 동의 여부
                @JsonProperty("ci_needs_agreement")
                public Boolean isCIAgree;
        
                //CI, 연계 정보
                @JsonProperty("ci")
                public String ci;
        
                //CI 발급 시각, UTC
                @JsonProperty("ci_authenticated_at")
                public Date ciCreatedAt;
        
                @Getter
                @NoArgsConstructor
                @JsonIgnoreProperties(ignoreUnknown = true)
                public class Profile {
        
                    //닉네임
                    @JsonProperty("nickname")
                    public String nickName;
        
                    //프로필 미리보기 이미지 URL
                    @JsonProperty("thumbnail_image_url")
                    public String thumbnailImageUrl;
        
                    //프로필 사진 URL
                    @JsonProperty("profile_image_url")
                    public String profileImageUrl;
        
                    //프로필 사진 URL 기본 프로필인지 여부
                    //true : 기본 프로필, false : 사용자 등록
                    @JsonProperty("is_default_image")
                    public String isDefaultImage;
        
                    //닉네임이 기본 닉네임인지 여부
                    //true : 기본 닉네임, false : 사용자 등록
                    @JsonProperty("is_default_nickname")
                    public Boolean isDefaultNickName;
        
                }
            }
        
            @Getter
            @NoArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            public class Partner {
                //고유 ID
                @JsonProperty("uuid")
                public String uuid;
            }
        
        }
        ```
        
6. 사용자 정보를 토대로 우리 서비스의 Token을 발급한다.
    - 해당 회원이 우리 서비스에 회원가입 되어있는지 확인하고, **기존 회원**이라면 **로그인**, **신규 회원**이라면 **회원가입 후 로그인**
    - 로그인이 되었기에 우리 서비스의 `AccessToken`과 `RefreshToken`이 발급된다.
    

현재 코드에선 카카오 서버 API에 RestTemplate을 통해 동기 요청을 보내는데, WebClient를 사용하면 비동기 요청으로 보낼 수 있다.

## 참고자료

[[Spring] REST API 카카오 로그인 구현하기](https://ddonghyeo.tistory.com/16)

# RefreshToken

---

## 구현 목록

로그인 시 AccessToken, RefreshToken 발급

RefreshToken 재발급 API

로그아웃 API

## 구현 방식

웹 기준 로그인 시 프론트에서

AccessToken은 메모리(js 변수, LocalStroage 등)에 저장하고, 

RefreshToken은 쿠키에 저장한다고 가정

간단한 구현을 위해 RefreshToken을 DB에 저장하여 관리(화이트리스트 방식, Redis로 개선필요)