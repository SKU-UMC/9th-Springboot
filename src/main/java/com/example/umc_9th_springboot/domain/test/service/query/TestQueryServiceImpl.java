package com.example.umc_9th_springboot.domain.test.service.query;

import com.example.umc_9th_springboot.domain.test.exception.TestException;
import com.example.umc_9th_springboot.domain.test.exception.code.TestErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestQueryServiceImpl implements TestQueryService {

    @Override
    public void checkFlag(Long flag) {
        if (flag != null && flag.equals(1L)) {   // ← 여기만 이렇게 바꿔주면 더 안전해
            throw new TestException(TestErrorCode.TEST_EXCEPTION);
        }
    }
}
