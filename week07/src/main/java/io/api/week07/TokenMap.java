package io.api.week07;

import java.util.HashMap;

/**
 * <pre>
 * io.api.moodify
 *   |_ TokenMap.java
 * </pre>
 *
 * Desc : Spotify Web API 이용을 위한 토큰 관리 클래스
 * @Author : kdh
 * @Date   : Nov 8, 2025
 */
public class TokenMap {

    /**	Spotify Web API 이용을 위한 토큰 저장 맵	*/
    private static HashMap<String, String> ACCESS_TOKEN_MAP = new HashMap<String, String>();
    /**	Spotify Web API 이용을 위한 토큰 유효기간 저장 맵	*/
    private static HashMap<String, Long> TOKEN_EXPIRY_MAP = new HashMap<String, Long>();

    /**
     * Desc : 토큰 저장
     * @Author : kdh
     * @Date   : Nov 8, 2025
     * @param clientId
     * @param accessToken
     * @param expiresIn
     */
    public static void setToken(String clientId, String accessToken, int expiresIn) {
        ACCESS_TOKEN_MAP.put(clientId, accessToken);

        long expiryTime = System.currentTimeMillis() + (expiresIn * 1000L);
        TOKEN_EXPIRY_MAP.put(clientId, expiryTime);
    }

    /**
     * Desc : 토큰 반환
     * @Author : kdh
     * @Date   : Nov 8, 2025
     * @param clientId
     * @return
     */
    public static String getToken(String clientId) {
        return ACCESS_TOKEN_MAP.get(clientId);
    }

    /**
     * Desc : 토큰 유효기간 반환
     * @Author : kdh
     * @Date   : Nov 8, 2025
     * @param clientId
     * @return
     */
    public static Long getExpiryTime(String clientId) {
        return TOKEN_EXPIRY_MAP.get(clientId);
    }
}
