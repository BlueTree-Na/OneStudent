package com.ncs.demo.config;

import com.ncs.demo.domain.board.Board;
import com.ncs.demo.domain.member.Member;
import com.ncs.demo.repository.boardRepository.BoardRepository;
import com.ncs.demo.repository.memberRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class InitData {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;


    @PostConstruct
    public void init(){

        Member member1 = new Member("nacs", "1234", "나청송", "BT");
        memberRepository.memberSave(member1);


        Board board1 = new Board(member1.getMemberManageSeq(), "HTML1", "zyzyzyy", "2023-06-14", member1.getNickName(), "Front-End");
        boardRepository.boardSave(board1);
        Board board2 = new Board(member1.getMemberManageSeq(), "HTML2", "1234", "2023-06-14", member1.getNickName(), "Front-End");
        boardRepository.boardSave(board2);

        Board board3 = new Board(member1.getMemberManageSeq(), "MVC1", "1234", "2023-06-14", member1.getNickName(), "Back-End");
        boardRepository.boardSave(board3);
        Board board4 = new Board(member1.getMemberManageSeq(), "MVC2", "1234", "2023-06-14", member1.getNickName(), "Back-End");
        boardRepository.boardSave(board4);

        Board board5 = new Board(member1.getMemberManageSeq(), "전처리1", "1234", "2023-06-14", member1.getNickName(), "AI");
        boardRepository.boardSave(board5);
        Board board6 = new Board(member1.getMemberManageSeq(), "전처리2", "1234", "2023-06-14", member1.getNickName(), "AI");
        boardRepository.boardSave(board6);

        Board board7 = new Board(member1.getMemberManageSeq(), "MySQL1", "1234", "2023-06-14", member1.getNickName(), "DB");
        boardRepository.boardSave(board7);
        Board board8 = new Board(member1.getMemberManageSeq(), "MySQL2", "1234", "2023-06-14", member1.getNickName(), "DB");
        boardRepository.boardSave(board8);

        Board board9 = new Board(member1.getMemberManageSeq(), "정렬1", "1234", "2023-06-14", member1.getNickName(), "ALGORITHM");
        boardRepository.boardSave(board9);
        Board board10 = new Board(member1.getMemberManageSeq(), "정렬2", "1234", "2023-06-14", member1.getNickName(), "ALGORITHM");
        boardRepository.boardSave(board10);

    }

}
