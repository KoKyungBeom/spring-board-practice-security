package com.springboot.reply.entity;

import com.springboot.board.entity.Board;
import com.springboot.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long replyId;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false,updatable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "BOARD_ID")
    private Board board;
}
