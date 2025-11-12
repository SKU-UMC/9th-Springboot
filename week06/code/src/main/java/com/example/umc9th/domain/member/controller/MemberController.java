package com.example.umc9th.domain.member.controller;

import com.example.umc9th.domain.member.dto.ResponseDto;
import com.example.umc9th.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {
    private final MemberService memberService;

    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<ResponseDto<Void>> deleteMember(@PathVariable Long memberId) {
        try{
            memberService.withdraw(memberId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto<>(HttpStatus.OK.value(), "회원 탈퇴 완료"));
        } catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
