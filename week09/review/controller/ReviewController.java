    package com.example.umc9th1.domain.review.controller;

    import com.example.umc9th1.domain.review.dto.ReviewRequestDto;
    import com.example.umc9th1.domain.review.dto.ReviewResponseDto;
    import com.example.umc9th1.domain.review.service.ReviewQueryService;
    import com.example.umc9th1.domain.review.service.ReviewService;
    import com.example.umc9th1.global.annotation.ValidMissionAccess;
    import com.example.umc9th1.global.annotation.ValidPage;
    import com.example.umc9th1.global.apipayload.ApiResponse;
    import com.example.umc9th1.global.apipayload.code.GeneralSuccessCode;
    import io.swagger.v3.oas.annotations.Operation;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import org.springframework.data.domain.Pageable;
    import java.math.BigDecimal;



    @RestController
    @RequestMapping("/users/{userId}")
    public class ReviewController {

        private final ReviewService reviewService;
        private final ReviewQueryService reviewQueryService;

        @Autowired
        public ReviewController(ReviewService reviewService, ReviewQueryService reviewQueryService) {
            this.reviewService = reviewService;
            this.reviewQueryService = reviewQueryService;
        }

        @PostMapping("/missions/{missionId}/reviews")
        public ResponseEntity<ApiResponse<ReviewResponseDto>> createReview(
                @PathVariable Long userId, //유저 아이디
                @RequestBody ReviewRequestDto dto, //리뷰 dto
                @ValidMissionAccess @PathVariable Long missionId ){ //미션 아이디

            ReviewResponseDto response=reviewService.createReview(userId,missionId , dto); //서비스 호출
            GeneralSuccessCode code  = GeneralSuccessCode.REVIEW_CREATED;
            return ResponseEntity.status(code.getStatus()).body(ApiResponse.onSuccess(code , response)); //201 create
        }

        @PostMapping("/missions/{missionId}/challenge")
        public ResponseEntity<ApiResponse<String>> challengeMission(
                @PathVariable Long userId,
                @PathVariable Long missionId
        ) {
          reviewService.challengeMission(userId, missionId);
            GeneralSuccessCode code  = GeneralSuccessCode.REVIEW_CREATED;
            return ResponseEntity.status(code.getStatus()).body(ApiResponse.onSuccess(code,"미션도전 성공"));
        }


//        @GetMapping("/reviews")
//        public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> searchMyReviews( @PathVariable Long userId,
//                                             @RequestParam(required = false) String storeName,
//                                             @RequestParam(required = false) BigDecimal star  ){
//
//            List<ReviewResponseDto> result = reviewQueryService.searchReview(userId,storeName,star);
//            GeneralSuccessCode code  = GeneralSuccessCode.SUCCESS;
//            return  ResponseEntity.status(code.getStatus()).body(ApiResponse.onSuccess(code,result));
//        }
        @GetMapping("/reviews")
        @Operation(summary = "나의 리뷰 조회", description = "storeName, star로 필터링 가능하며 page는 1부터 시작합니다.")
        public ResponseEntity<ApiResponse<?>> searchMyReviews(
        @PathVariable Long userId,
        @RequestParam(required = false) String storeName,
        @RequestParam(required = false) BigDecimal star,
        @ValidPage Pageable pageable
        ) {

            var result = reviewQueryService.searchReview(userId, storeName, star, pageable);

            GeneralSuccessCode code = GeneralSuccessCode.SUCCESS;
            return ResponseEntity.status(code.getStatus()).body(ApiResponse.onSuccess(code, result));
        }




    }
