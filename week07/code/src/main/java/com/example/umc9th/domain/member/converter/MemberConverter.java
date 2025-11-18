package com.example.umc9th.domain.member.converter;

import com.example.umc9th.domain.member.dto.res.MemberResDTO;

public class MemberConverter {

    public static MemberResDTO.Delete toDeleteDTO(Long memeberId){
        return MemberResDTO.Delete.builder()
                .memeberId(memeberId)
                .build();
    }
}
