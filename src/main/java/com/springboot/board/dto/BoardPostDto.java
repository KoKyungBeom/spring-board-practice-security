package com.springboot.board.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class BoardPostDto {
    @Positive
    private long memberId;
    @NotNull
    private String title;
    @NotNull
    private String content;
}
