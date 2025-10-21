package com.example.umc9th1.domain.entity.others;


import com.example.umc9th1.global.baseentity.BaseIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Entity
@Table(name = "location")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location extends BaseIdEntity {
   @Column(name = "address" , nullable = false)
    private String address;

   public Location(@NonNull String address){
       this.address = address;
   }


}
