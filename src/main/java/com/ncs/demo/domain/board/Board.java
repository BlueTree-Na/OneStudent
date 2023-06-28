package com.ncs.demo.domain.board;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Getter @Setter
public class Board {

    private Long boardManageSeq; // 게시글 관리 번호
    private Long writerManageSeq; // 작성자 관리 번호

    @NotEmpty
    @NotNull
    private String title; // 글 제목

    @NotEmpty
    @NotNull
    private String content; // 글 내용

    @NotEmpty
    @NotNull
    private String date; // 글 작성 날짜

    @NotEmpty
    @NotNull
    private String writerNickname; // 작성자

    @NotEmpty
    @NotNull
    private String field; // 게시글 분야

    public Board(Long writerManageSeq, String title, String content, String date, String writerNickname, String field) {
        this.writerManageSeq = writerManageSeq;
        this.title = title;
        this.content = content;
        this.date = date;
        this.writerNickname = writerNickname;
        this.field = field;
    }
}
