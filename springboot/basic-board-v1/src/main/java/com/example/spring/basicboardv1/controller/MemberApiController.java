package com.example.spring.basicboardv1.controller;

import com.example.spring.basicboardv1.dto.SignUpRequestDTO;
import com.example.spring.basicboardv1.dto.SignUpResponseDTO;
import com.example.spring.basicboardv1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 기능 처리
@RestController
@RequiredArgsConstructor // AutoWired를 실무에서 쓰면 안되는 이유 = 아래 주석과 연관있음
public class MemberApiController {

    private final MemberService memberService; // 프로젝트가 커졌을 때 여러 곳에서 변경이 일어나면 문제가 생겼을 때 어디가 문제인지 알 수가 없기 때문에 final 붙여야 함
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public SignUpResponseDTO join(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        System.out.println("SignUpRequestDTO :: " + signUpRequestDTO);
        memberService.signUp(signUpRequestDTO.toMember(bCryptPasswordEncoder));
        return SignUpResponseDTO.builder()
                .build();
    }

//    @PostMapping("/login")
//    public SignInResponseDTO join(@RequestBody SignInRequestDTO signInRequestDTO) {
//        System.out.println("signInRequestDTO :: " + signInRequestDTO);
//        return SignInResponseDTO.builder()
//                .build();
//    }

}
