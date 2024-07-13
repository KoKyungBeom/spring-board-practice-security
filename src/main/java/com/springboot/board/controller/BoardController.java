package com.springboot.board.controller;

import com.springboot.board.dto.BoardPatchDto;
import com.springboot.board.dto.BoardPostDto;
import com.springboot.board.dto.BoardResponseDto;
import com.springboot.board.dto.LikePostDto;
import com.springboot.board.entity.Board;
import com.springboot.board.mapper.BoardMapper;
import com.springboot.board.service.BoardService;
import com.springboot.response.MultiResponseDto;
import com.springboot.response.SingleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/boards")
@Validated
public class BoardController {
    private final BoardService boardService;
    private final BoardMapper boardMapper;

    public BoardController(BoardService boardService, BoardMapper boardMapper) {
        this.boardService = boardService;
        this.boardMapper = boardMapper;
    }

    @PostMapping
    public ResponseEntity postBoard(@Valid @RequestBody BoardPostDto boardPostDto){
        Board board = boardService.createBoard(boardMapper.boardPostDtoToBoard(boardPostDto));
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @PostMapping("/like/{board-id}")
    public ResponseEntity postLike(@PathVariable("board-id") @Positive long boardId,
                                   @RequestBody LikePostDto likePostDto){
        likePostDto.setBoardId(boardId);

        boardService.toggleLike(boardMapper.likePostDtoToLike(likePostDto));

        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/{board-id}")
    public ResponseEntity patchBoard(@PathVariable("board-id") @Positive long boardId,
                                     @Valid @RequestBody BoardPatchDto boardPatchDto){
        boardPatchDto.setBoardId(boardId);

        Board board = boardService.updateBoard(boardMapper.boardPatchDtoToBoard(boardPatchDto));

        return new ResponseEntity(new SingleResponseDto<>(boardMapper.boardToboardResponseDto(board)),HttpStatus.OK);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity getBoard(@PathVariable("board-id") @Positive long boardId){
        Board board = boardService.findBoard(boardId);
        BoardResponseDto response = boardMapper.boardToboardResponseDto(board);
        return new ResponseEntity(new SingleResponseDto<>(response),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity getBoards(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size){
        Page<Board> pageBoards = boardService.findBoards(page-1,size);
        List<Board> boards = pageBoards.getContent();

        return new ResponseEntity(new MultiResponseDto<>(boardMapper.boardsToBoardResponseDtos(boards),pageBoards),HttpStatus.OK);
    }
    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteMember(@PathVariable("board-id") @Positive long boardId){
        boardService.deleteBoard(boardId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
