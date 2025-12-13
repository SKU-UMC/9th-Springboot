package io.api.week07;

import io.api.week07.Code.ErrorCode;
import io.api.week07.Code.ResponseCode;
import io.api.week07.Dto.ErrorResponseDTO;
import io.api.week07.Dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/spotify")
@RequiredArgsConstructor
public class SpotifyController {

    private final Connector connector;

    @Value("${spotify.client_id}")
    private String clientId;

    @Value("${spotify.client_secret}")
    private String clientSecret;

    @GetMapping("/token")
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
