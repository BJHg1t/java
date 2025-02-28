package com.example.spring.basicboardv1.controller;

import com.example.spring.basicboardv1.dto.SignUpRequsetDTO;
import com.example.spring.basicboardv1.dto.SignUpResponseDTO;
import com.example.spring.basicboardv1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 기능 처리
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService; // 프로젝트가 커졌을 때 여러 곳에서 변경이 일어나면 문제가 생겼을 때 어디가 문제인지 알 수가 없음 그래서 final 붙여야 함

    @PostMapping("/join")
    public SignUpResponseDTO join(@RequestBody SignUpRequsetDTO signUpRequsetDTO) {
        System.out.println("SignUpRequsetDTO :: " + signUpRequsetDTO);
        return SignUpResponseDTO.builder()
                .build();
    }

}
