    package com.example.umc9th1.domain.entity.others;

    import com.example.umc9th1.global.baseentity.BaseIdEntity;
    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.Table;
    import lombok.*;


    @Entity
    @Table(name = "term")
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class Term extends BaseIdEntity {

        @Column(name = "term_name",nullable = false,length = 20)
        private String name;


        public Term(@NonNull String name){
            this.name = name;
        }

    }
