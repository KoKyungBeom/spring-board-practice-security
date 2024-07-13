package com.springboot.board.mapper;

import com.springboot.board.dto.BoardPatchDto;
import com.springboot.board.dto.BoardPostDto;
import com.springboot.board.dto.BoardResponseDto;
import com.springboot.board.dto.LikePostDto;
import com.springboot.board.entity.Board;
import com.springboot.board.entity.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    @Mapping(source = "memberId",target = "member.memberId")
    Board boardPostDtoToBoard(BoardPostDto boardPostDto);
    @Mapping(source = "member.name",target = "username")
    @Mapping(source = "reply.comment",target = "comment")
    BoardResponseDto boardToboardResponseDto(Board board);
    Board boardPatchDtoToBoard(BoardPatchDto boardPatchDto);
    List<BoardResponseDto> boardsToBoardResponseDtos(List<Board> boards);
    @Mapping(source = "memberId",target = "member.memberId")
    @Mapping(source = "boardId",target = "board.boardId")
    Like likePostDtoToLike (LikePostDto likePostDto);
}
