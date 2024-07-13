package com.springboot.reply.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ReplyResponseDto {
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
