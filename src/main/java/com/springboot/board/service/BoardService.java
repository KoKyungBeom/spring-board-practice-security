package com.springboot.board.service;

import com.springboot.board.entity.Board;
import com.springboot.board.entity.Like;
import com.springboot.board.repository.BoardRepository;
import com.springboot.board.repository.LikeRepository;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import com.springboot.reply.service.ReplyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final ReplyService replyService;
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    public BoardService(BoardRepository boardRepository, ReplyService replyService, LikeRepository likeRepository, MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.replyService = replyService;
        this.likeRepository = likeRepository;
        this.memberRepository = memberRepository;
    }

    public Board createBoard(Board board){
        return boardRepository.save(board);
    }

    public Board updateBoard(Board board){
        Board findBoard = findVerifiedBoard(board.getBoardId());

        Optional.ofNullable(board.getTitle())
                .ifPresent(title -> findBoard.setTitle(title));
        Optional.ofNullable(board.getContent())
                .ifPresent(content -> findBoard.setContent(content));
        Optional.ofNullable(board.getPrivacyStatus())
                .ifPresent(privacyStatus -> findBoard.setPrivacyStatus(privacyStatus));

        board.setModifiedAt(LocalDateTime.now());

        return boardRepository.save(findBoard);
    }
    public Board findBoard(long boardId){
        Board board = findVerifiedBoard(boardId);
        if(board.getQuestionStatus() == Board.QuestionStatus.QUESTION_DELETED){
            throw new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND);
        }
        return findVerifiedBoard(boardId);
    }
    public Page<Board> findBoards(int page, int size){
        return boardRepository.findAll(PageRequest.of(page,size, Sort.Direction.DESC,"board-id"));
    }

    public void deleteBoard(long boardId){
        Board findBoard = findVerifiedBoard(boardId);
        findBoard.setQuestionStatus(Board.QuestionStatus.QUESTION_DELETED);

        if(findBoard.getReply() != null) {
            replyService.deleteReply(findBoard.getReply().getReplyId());
        }
        boardRepository.save(findBoard);
    }
    public void toggleLike(Like like) {
        Optional<Board> board = boardRepository.findById(like.getBoard().getBoardId());
        Optional<Member> member = memberRepository.findById(like.getMember().getMemberId());

        Board findBoard = board.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        Member findMember = member.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Optional<Like> optionalLike = likeRepository.findByMemberAndBoard(
            findMember, findBoard);

        if(optionalLike.isPresent()) {
            Like findLike = optionalLike.orElseThrow(() -> new BusinessLogicException(ExceptionCode.REPLY_EXISTS));
            findMember.removeLike(findLike);
            findBoard.removeLike(findLike);
            likeRepository.delete(findLike);
        } else {
            Like addLike = new Like();
            addLike.setBoard(findBoard);
            addLike.setMember(findMember);
            likeRepository.save(addLike);
        }
    }
    public Board findVerifiedBoard(long boardId){
        Optional<Board> Board = boardRepository.findById(boardId);
        return Board.orElseThrow(()-> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }
}
