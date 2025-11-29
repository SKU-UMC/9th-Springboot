package com.example.umc_9th_springboot.domain.mission.exception;

import com.example.umc_9th_springboot.domain.mission.exception.code.MissionErrorCode;
import com.example.umc_9th_springboot.global.apiPayload.exception.GeneralException;

public class MissionException extends GeneralException {
    public MissionException(MissionErrorCode errorCode) {
        super(errorCode);
    }
}
