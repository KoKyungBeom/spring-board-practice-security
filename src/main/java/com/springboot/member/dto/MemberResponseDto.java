package com.springboot.member.dto;

import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class MemberResponseDto {
    private String email;
    private String name;
    private String phone;
}
