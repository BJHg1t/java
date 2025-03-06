package com.example.spring.basicboardv2.type;

import lombok.Getter;

@Getter
// 숫자로 관리했을 때 추후에 data가 추가되면 순서가 밀려서 일반 유저가 관리자가 되는 경우가 생길 수 있으므로 문자열로 관리하는게 좋음
public enum Role {
    ROLE_USER,
    ROLE_ADMIN
}
