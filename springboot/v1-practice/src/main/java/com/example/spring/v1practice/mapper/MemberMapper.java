package com.example.spring.v1practice.mapper;

import com.example.spring.v1practice.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void insertMember(Member member);
    Member selectMemberByUserId(String userId);
}
