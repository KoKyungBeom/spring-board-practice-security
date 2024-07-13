package com.springboot.reply.mapper;

import com.springboot.reply.dto.ReplyPatchDto;
import com.springboot.reply.dto.ReplyPostDto;
import com.springboot.reply.dto.ReplyResponseDto;
import com.springboot.reply.entity.Reply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReplyMapper {
    @Mapping(source = "boardId",target = "board.boardId")
    @Mapping(source = "memberId",target = "member.memberId")
    Reply replyPostDtoToReply (ReplyPostDto replyPostDto);
    Reply replyPatchDtoToReply(ReplyPatchDto replyPatchDto);
    ReplyResponseDto replyToReplyResponseDto (Reply reply);
}
