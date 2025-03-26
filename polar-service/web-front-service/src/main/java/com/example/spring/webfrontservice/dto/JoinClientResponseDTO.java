package com.example.spring.webfrontservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// isSuccess 값 true로 넘어오는데 @Setter가 없으면 수정할 방법이 없어서 값이 기본값인 false가 되버림
// auth-service에서 값을 받아오는 clientDTO는 전부 추가해야 함
public class JoinClientResponseDTO {
    private boolean isSuccess;

    public JoinResponseDTO toJoinResponseDTO() {
        return JoinResponseDTO.builder()
                .isSuccess(isSuccess)
                .url(isSuccess ? "/webs/login" : "/webs/join")
                .build();
    }
}
