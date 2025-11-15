package com.example.umc_9th_springboot.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** QueryDSL JPAQueryFactory 빈 등록 */
@Configuration
public class QuerydslConfig {
    @PersistenceContext
    private EntityManager em;

    /** JPAQueryFactory 생성 */
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
