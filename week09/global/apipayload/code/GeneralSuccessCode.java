package com.example.umc9th1.global.apipayload.code;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralSuccessCode implements BaseSuccessCode {

    //공통기능
    SUCCESS(HttpStatus.OK , "COMMON200_1","요청이 성공적으로 처리되었습니다."),

    //유저 기능
    USER_CREATED(HttpStatus.CREATED, "USER201_1", "회원이 성공적으로 생성되었습니다."),
    USER_UPDATED(HttpStatus.OK, "USER200_1", "회원 정보가 성공적으로 수정되었습니다."),
    USER_DELETED(HttpStatus.OK, "USER200_2", "회원이 성공적으로 삭제되었습니다."),

    //인증 기능
    LOGIN_SUCCESS(HttpStatus.OK, "AUTH200_1", "로그인에 성공했습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "AUTH200_2", "로그아웃에 성공했습니다."),

    //리뷰 관련 기능
    REVIEW_CREATED(HttpStatus.CREATED , "REVIEW201_1" , "리뷰가 성공적으로 생성되었습니다."),
    REVIEW_UPDATED(HttpStatus.OK , "REVIEW200_1","리뷰가 성공적으로 수정되었습니다."),
    REVIEW_DELETED(HttpStatus.OK,"REVIEW200_2","리뷰가 성공적으로 삭제되었습니다."),

    //리뷰 사진 기능
    PHOTO_CREATED(HttpStatus.CREATED , "PHOTO201_1" , "사진이 성공적으로 등록되었습니다."),
    PHOTO_UPDATED(HttpStatus.OK , "PHOTO200_1" , "사진이 성공적으로 수정되었습니다."),
    PHOTO_DELETED(HttpStatus.OK , "PHOTO200_2" , "사진이 성공적으로 삭제되었습니다."),

    //리뷰 댓글 기능
    COMMENT_CREATED(HttpStatus.CREATED , "COMMENT201_1" , "댓글이 성공적으로 생성되었습니다."),
    COMMENT_UPDATED(HttpStatus.OK , "COMMENT200_1","댓글이 성공적으로 수정되었습니다."),
    COMMENT_DELETED(HttpStatus.OK,"COMMENT200_2","댓글이 성공적으로 삭제되었습니다.");






    private final HttpStatus status;
    private final String code;
    private final String message;
}
