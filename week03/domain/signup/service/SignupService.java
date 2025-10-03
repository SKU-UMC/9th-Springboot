package com.example.umc9th.domain.signup.service;

import com.example.umc9th.domain.region.repository.RegionRepository;
import com.example.umc9th.domain.signup.dto.SignupDTO;
import com.example.umc9th.domain.signup.food.repository.FoodRepository;
import com.example.umc9th.domain.signup.term.repository.TermRepository;
import com.example.umc9th.domain.user.entity.UserFood;
import com.example.umc9th.domain.user.entity.UserTerm;
import com.example.umc9th.domain.user.repository.UserFoodRepository;
import com.example.umc9th.domain.user.repository.UserTermRepository;
import com.example.umc9th.domain.user.repository.UserRepository;
import com.example.umc9th.global.entity.Region;
import com.example.umc9th.global.entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SignupService {

    private final TermRepository termRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final UserTermRepository userTermRepository;
    private final UserFoodRepository userFoodRepository;
    private final RegionRepository regionRepository;

    @Autowired
    public SignupService(TermRepository termRepository,
                         FoodRepository foodRepository,
                         UserRepository userRepository,
                         UserTermRepository userTermRepository,
                         UserFoodRepository userFoodRepository,
                         RegionRepository regionRepository) {
        this.termRepository = termRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
        this.userTermRepository = userTermRepository;
        this.userFoodRepository = userFoodRepository;
        this.regionRepository = regionRepository;
    }

    // 회원가입 완료
    @Transactional
    public void signup(SignupDTO dto) {
        // 1. Users 테이블 저장 (setter 사용)
        Users user = new Users();
        user.setName(dto.getName());
        user.setGender(dto.getGender());
        user.setBirth(dto.getBirth());
        user.setDetailAddress(dto.getDetailAddress());
        user.setEmail(dto.getEmail());
        user.setPhoneNum(dto.getPhoneNumber());
        Region region = regionRepository.getReferenceById(dto.getLocationId());
        user.setLocation(region);
        user.setSocial(dto.getSocial());
        user.setPoint(dto.getPoint());

        userRepository.save(user);

        // 2. UserTerm 테이블 저장
        List<UserTerm> userTerms = dto.getTermIds()
                .stream()
                .map(termId -> {
                    UserTerm userTerm = new UserTerm();
                    userTerm.setUser(user);
                    userTerm.setTerm(termRepository.getReferenceById(termId));
                    return userTerm;
                })
                .collect(Collectors.toList());
        userTermRepository.saveAll(userTerms);

        // 3. UserFood 테이블 저장
        List<UserFood> userFoods = dto.getFoodIds()
                .stream()
                .map(foodId -> {
                    UserFood userFood = new UserFood();
                    userFood.setUser(user);
                    userFood.setFood(foodRepository.getReferenceById(foodId));
                    return userFood;
                })
                .collect(Collectors.toList());
        userFoodRepository.saveAll(userFoods);
    }
}

