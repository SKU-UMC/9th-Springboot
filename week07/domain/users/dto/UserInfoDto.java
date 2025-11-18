package com.example.umc9th1.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    String name; //이름
    String email; //이메일
    String phoneNum; //전화번호
    int point; //포인트

}
