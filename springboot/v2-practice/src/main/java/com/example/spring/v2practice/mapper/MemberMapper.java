package com.example.spring.v2practice.mapper;

import com.example.spring.v2practice.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void saved (Member member);
    Member findByUserId(String userId);
}
