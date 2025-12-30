    package com.example.umc9th1.domain.users.entity;

    import com.example.umc9th1.domain.entity.others.Location;
    import com.example.umc9th1.domain.enums.GenderType;
    import com.example.umc9th1.domain.enums.SocialType;
    import com.example.umc9th1.domain.enums.Status;
    import com.example.umc9th1.domain.users.enums.Role;
    import com.example.umc9th1.global.baseentity.BaseIdAndTimeEntity;
    import jakarta.persistence.*;
    import lombok.*;


    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;


    @Entity
    @Table(name = "users")
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public class Users extends BaseIdAndTimeEntity {

        @Column(name = "name", nullable = false, length = 100)
        private String name;

        @Enumerated(EnumType.STRING)
        @Column(name = "gender", nullable = false)
        private GenderType genderType;

        @Column(name = "birth" , nullable = false)
        private LocalDate birth;

        @Column(name = "detail_address", length = 254 ,  nullable = false)
        private String detailAddress;

        @Enumerated(EnumType.STRING)
        @Column(name ="social")
        private SocialType socialType;

        @Column(name = "point",nullable = false)
        private int point = 0;

        @Column(name = "phone_num",length = 20)
        private String phoneNum;

        @Column(name = "email" , nullable = false ,length = 254)
        private String email;

        @Column(name = "password" , nullable = false)
        private String password;

        @Enumerated(EnumType.STRING)
        private Role role;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "location_id", nullable = false)
        private Location location;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", nullable = false)
        private Status status = Status.ACTIVE;

        @Column(name = "inactive_date")
        private LocalDateTime inactiveDate;

        @OneToMany(mappedBy = "user" , cascade = CascadeType.REMOVE)
        private List<UserMission>  userMissionList = new ArrayList<>();

        public Users(@NonNull String name,
                     @NonNull GenderType genderType,
                     @NonNull LocalDate birth,
                     @NonNull String email,
                     @NonNull Location location,
                     @NonNull String detailAddress
                     ){
            this.name = name;
            this.genderType = genderType;
            this.birth = birth;
            this.email = email;
            this.location=location;
            this.detailAddress = detailAddress;
        }

        @Builder
        public Users(String phoneNum,
                     SocialType socialType){
            this.phoneNum = phoneNum;
            this.socialType = socialType;
        }


        public void inactive() {
            this.status = Status.INACTIVE;
            this.inactiveDate = LocalDateTime.now();
        }

        public void addPoint(int point) {
            this.point += point;
        }

        public void updatePhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public void updateSocialType(SocialType socialType) {
            this.socialType = socialType;
        }

        public void changePassword(String encodedPassword) {
            this.password = encodedPassword;
        }

        public void assignRole(Role role) {
            this.role = role;
        }

    }
