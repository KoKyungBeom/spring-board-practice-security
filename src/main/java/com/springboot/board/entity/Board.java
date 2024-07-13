package com.springboot.board.entity;

import com.springboot.member.entity.Member;
import com.springboot.reply.entity.Reply;
import com.springboot.reply.repository.ReplyRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    @Column(nullable = false,length = 100)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false,updatable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne(mappedBy = "board")
    private Reply reply;

    @OneToOne(mappedBy = "board",cascade = CascadeType.MERGE)
    private Like like;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QuestionStatus questionStatus = QuestionStatus.QUESTION_REGISTERED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private PrivacyStatus privacyStatus = PrivacyStatus.PRIVACY_PUBLIC;

    public void setMember(Member member){
        if(!member.getBoards().contains(this)){
            member.getBoards().add(this);
        }
        this.member = member;
    }
    public void setLike(Like like){
        if(like != null) {
            if(like.getBoard() != this){
                like.setBoard(this);
            }
            this.like = like;
        }
    }
    public void removeLike(Like like){
        this.like = null;
        if(like.getBoard() == this){
            like.removeBoard(this);
        }
    }
    public enum QuestionStatus{
        QUESTION_REGISTERED("질문 등록 상태"),
        QUESTION_ANSWERED("답변 완료 상태"),
        QUESTION_DELETED("질문 삭제 상태"),
        QUESTION_DEACTIVED("질문 비활성화 상태");

        @Getter
        private String status;

        QuestionStatus(String status) {
            this.status = status;
        }
    }
    public enum PrivacyStatus{
        PRIVACY_PUBLIC("공개 상태"),
        PRIVACY_SECRET("비공개 상태");

        @Getter
        private String status;
        PrivacyStatus(String status) {
            this.status = status;
        }
    }
}
