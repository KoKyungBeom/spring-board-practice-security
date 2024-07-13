package com.springboot.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LikePostDto {
    private long memberId;
    private long boardId;
}
