package com.example.umc9th1.domain.entity.store;

import com.example.umc9th1.domain.enums.StoreCategoryType;
import com.example.umc9th1.global.baseentity.BaseIdEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name ="store_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCategory extends BaseIdEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "store_category_name" , nullable = false)
    private StoreCategoryType name;

    public StoreCategory(@NonNull StoreCategoryType type){
        this.name = type;
    }
}
