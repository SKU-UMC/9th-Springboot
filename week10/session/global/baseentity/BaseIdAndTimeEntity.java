package com.example.umc9th1.global.baseentity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BaseIdAndTimeEntity extends BaseIdEntity{
    @CreatedDate
    @Column(name = "create_at", updatable = false, nullable = false)
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name="change_at")
    private LocalDateTime changeAt;

    @Column(name="delete_at")
    private LocalDateTime deleteAt;

    public void softDelete() {
        this.deleteAt = LocalDateTime.now();
    }




}
