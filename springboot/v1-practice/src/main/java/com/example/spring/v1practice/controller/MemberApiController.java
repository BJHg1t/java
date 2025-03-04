package com.example.spring.v1practice.controller;

import com.example.spring.v1practice.dto.SignInRequestDTO;
import com.example.spring.v1practice.dto.SignInResponseDTO;
import com.example.spring.v1practice.dto.SignUpRequestDTO;
import com.example.spring.v1practice.dto.SignUpResponseDTO;
import com.example.spring.v1practice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public SignUpResponseDTO join(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        System.out.println("SignUpRequestDTO :: " + signUpRequestDTO);
        memberService.signUp(signUpRequestDTO.toMember(bCryptPasswordEncoder));
        return SignUpResponseDTO.builder()
                .build();
    }

    @PostMapping("/login")
    public SignInResponseDTO login(@RequestBody SignInRequestDTO signInRequestDTO) {
        System.out.println("SignInRequestDTO :: " + signInRequestDTO);
        return SignInResponseDTO.builder()
                .build();
    }
}
