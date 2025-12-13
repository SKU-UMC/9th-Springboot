package io.api.week07;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * <pre>
 * io.api.moodify
 *   |_ Connector.java
 * </pre>
 *
 * Desc : Spotify Web API 엑세스 토큰 및 상품 조회를 위한 HTTP 요청 클래스
 * @Author : kdh
 * @Date   : Nov 8, 2025
 */
@Service
public class Connector {
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Desc : Spotify Web API 엑세스 토큰 발급 요청
     * @Author : kdh
     * @Date   : Nov 8, 2025
     * @param clientId
     * @param clientSecret
     * @return
     */
    protected static HashMap<String, Object> publishToken(String clientId, String clientSecret) {
        try {
            HttpPost httpPost = new HttpPost(Constant.OAUTH_DOMAIN + Constant.GET_TOKEN);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

            String body = "grant_type=client_credentials" +
                    "&client_id=" + clientId +
                    "&client_secret=" + clientSecret;
            httpPost.setEntity(new StringEntity(body, ContentType.parse("UTF-8")));

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpPost);

            String responseJson = EntityUtils.toString(response.getEntity(), "UTF-8");

            response.close();
            httpClient.close();

            HashMap<String, Object> tokenMap = mapper.readValue(responseJson, new TypeReference<HashMap<String, Object>>() {});
            return tokenMap;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Desc : 엑세스 토큰 반환
     * @Author : kdh
     * @Date   : Nov 8, 2025
     * @param clientId
     * @param clientSecret
     * @return
     * @throws IOException
     */
    public String getToken(String clientId, String clientSecret) throws IOException {
        String accessToken = TokenMap.getToken(clientId);
        Long expiryTime = TokenMap.getExpiryTime(clientId);
        if (accessToken == null || "".equals(accessToken) || !checkToken(expiryTime)) {
            HashMap<String, Object> tokenMap = publishToken(clientId, clientSecret);
            if (tokenMap != null) {
                String newToken = (String) tokenMap.get("access_token");
                int expiresIn = (int) tokenMap.get("expires_in");
                TokenMap.setToken(clientId, newToken, expiresIn);
                accessToken = newToken;
            }
        }

        return accessToken;
    }

    /**
     * Desc : 토큰 유효기간 확인
     * @param expiryTime
     * @return
     */
    private static boolean checkToken(Long expiryTime) {
        long now = new Date().getTime();
        if (now > expiryTime || expiryTime - now < 600000) {
            return false;
        }
        System.out.println("time = " + (expiryTime - now));

        return true;
    }
}
