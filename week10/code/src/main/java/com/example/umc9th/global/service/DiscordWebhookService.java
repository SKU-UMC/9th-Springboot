package com.example.umc9th.global.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscordWebhookService {
    //Webhook URL을 환경변수에 저장
    @Value("${alert.discord.webhook-url}")
    private String webhookUrl;

    //local환경이면 enabled:false, dev환경이면 enabled:true
    @Value("${alert.discord.enabled}")
    private boolean webhookEnabled;

    //Spring Framework에서 제공하는 동기(Synchronous) 방식의 HTTP 클라이언트
    //다른 서버의 API를 호출할 때 사용
    private final RestTemplate restTemplate = new RestTemplate();

    public void sendErrorToWebhook(Exception ex, String url, String method) {
        try {
            if(webhookEnabled){
                String message = buildMessage(ex, url, method);

                //HTTP Header 정의, Content-Type: application/json
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Discord body 형식({content: 내용})
                Map<String, String> payload = Map.of("content", message);

                //header + body
                HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);

                //webhookUrl에 entity내용을 post로 전송
                restTemplate.postForEntity(webhookUrl, entity, String.class);
            }

        } catch (Exception e) {
            log.error("Failed to send webhook: {}", e.getMessage());
        }
    }

    // Discord에 보낼 메시지 형식
    private String buildMessage(Exception ex, String url, String method) {
        return """
                🚨 **500 Internal Server Error 발생**
                - URL: `%s`
                - Method: `%s`
                - 예외: `%s`
                - 시각: `%s`
                """.formatted(
                url,
                method,
                ex.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }


}
