package com.example.umc9th.domain.signup.contoller;


import com.example.umc9th.domain.signup.dto.SignupDTO;
import com.example.umc9th.domain.signup.food.dto.FoodResponseDTO;
import com.example.umc9th.domain.signup.food.service.FoodService;
import com.example.umc9th.domain.signup.service.SignupService;
import com.example.umc9th.domain.signup.term.dto.TermResponseDTO;
import com.example.umc9th.domain.signup.term.service.TermService;
import com.example.umc9th.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/signup")
public class SignUpController {

    private final SignupService signupService;
    private final TermService termService;
    private final FoodService  foodService;

    @Autowired
    public SignUpController(SignupService signupService,
                            FoodService    foodService,
                            TermService termService) {
        this.signupService = signupService;
        this.foodService = foodService;
        this.termService = termService;
    }

    // GET: 약관 조회
    @GetMapping("/terms")
    public ResponseEntity<List<TermResponseDTO>> getTerms() {
        List<TermResponseDTO> terms = termService.getAllTerms();
        return ResponseEntity.ok(terms);
    }

    // GET: 음식 조회
    @GetMapping("/foods")
    public ResponseEntity<List<FoodResponseDTO>> getFoods() {
        List<FoodResponseDTO> foods = foodService.getAllFoods();
        return ResponseEntity.ok(foods);
    }

    // POST: 회원가입 완료
    @PostMapping
    public ResponseEntity<String> signup(@RequestBody SignupDTO signupDTO) {
        signupService.signup(signupDTO);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

}
