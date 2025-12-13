package io.api.oauth2;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {
    // 롬복의 value아닌 빈스 팩토리의 벨류
// 이게 프로퍼티에 있는 시크릿키를 가져오는 어노테이션
    @Value("${secret-key}")
    private String secretKey;
    public String createToken(String userId){
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        //Key 설정을 해줘야함
        //jwt는 헤더, 페이로드, 시그니처로 구성되어있고
        // 헤더는 시그니쳐랑 페이로드를 조합해서 알아서 생성된다고 했음
        // 그래서 헤더는? 만들필요가없다~
        // 그럼 만들어야될거?
        // payload에 실어 보낼 값과 시그니쳐를 만들면됨
        // secretKey를 바탕으로 HMAC-SHA 키 -> 시그니쳐 생성.
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        //jwt 만들고 반환.
        return Jwts.builder()
                //JWT 생성
                //자 시작은 무슨 키로했는지 내가 쓴 알고리즘이 뭔지
                // 이걸로 signWith로 시작하는게 규칙
                .signWith(key, SignatureAlgorithm.HS256)
                // 다음 내가 jwt토큰에 실어서 보낼 정보 입력.
                // jwt토큰은 탈취당하는 위험을
                // 토큰의 파기. 리프레시로 결정한다.
                // 일단 setExpiration을 쓰고(아까 만들어논 expiredDate를 설정.
                // 일단 userId를 실어 보냄
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();
    }
    // 여기까지 jwt를 만들었음
    // 검증을 해야되지 -> 검증 메서드를 만들자.
    // 검증은 보통 벨리데이트 라고
    // jwt 검증 메서드
    public String validate(String jwt){
        String subject;
        //Signature 생성
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        try {
            subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    // parseClaimsJwt와 Jws가 있는데
                    // 여기보면 jwt는 받아오는 인자가 object랑 key
                    // jws는 key만
                    // 우리는 파라미터 인자가 jwt하나
                    // 그니까 jws를 사용해야함
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        }catch (Exception e){
            // 로그관리해주는 어노테이션 추가해서 로그관리
            // @Slf4j 추가 그리고 getMesage()로 바꿈
            // 로그의 레벨설정
            e.printStackTrace();
            return null;
        }
        return subject;
    }
}