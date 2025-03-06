package com.example.spring.v2practice.service;

import com.example.spring.v2practice.mapper.MemberMapper;
import com.example.spring.v2practice.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public void signUp(Member member) {
        memberMapper.saved(member);
    }
}
