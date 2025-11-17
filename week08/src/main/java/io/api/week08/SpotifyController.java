package io.api.week8;

import io.api.week8.Code.ErrorCode;
import io.api.week8.Code.ResponseCode;
import io.api.week8.Dto.ErrorResponseDTO;
import io.api.week8.Dto.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Tag(name = "토큰 API")
@RequestMapping("/api/spotify")
@RequiredArgsConstructor
public class SpotifyController {

    private final Connector connector;

    @Value("${spotify.client_id}")
    private String clientId;

    @Value("${spotify.client_secret}")
    private String clientSecret;

    @GetMapping("/token")
    @Operation(summary = "토큰 발급", description = "토큰을 발급합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    public ResponseEntity<?> getSpotifyToken() {
        try {
            String token = connector.getToken(clientId, clientSecret);
            return ResponseEntity.status(ResponseCode.SUCCESS_GET_TOKEN.getStatus().value())
                    .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_TOKEN, token));
        } catch (IOException e) {
            return ResponseEntity.status(ErrorCode.INVALID_CLIENT_ID.getStatus().value())
                    .body(new ErrorResponseDTO(ErrorCode.INVALID_CLIENT_ID));
        }
    }
}
