package com.springboot.reply.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Getter
public class ReplyPostDto {
    @Positive
    private long boardId;
    @Positive
    private long memberId;
    @NotNull
    private String comment;
}
