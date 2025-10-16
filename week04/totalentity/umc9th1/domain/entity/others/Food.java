package com.example.umc9th1.domain.entity.others;

import com.example.umc9th1.global.baseentity.BaseIdEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "food")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends BaseIdEntity {
    @Column(name = "food_name",nullable = false,length = 20)
    private String name;


    public Food(@NonNull String name){
        this.name = name;
    }
}
