package com.ncs.demo.controller.board;

import com.ncs.demo.config.session.SessionConst;
import com.ncs.demo.config.session.SessionForm;
import com.ncs.demo.domain.board.Board;
import com.ncs.demo.repository.boardRepository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor

public class BoardController {

    private final BoardRepository boardRepository;

    // 게시글 자세히 보기
    @GetMapping("/board/{boardNum}")
    public String detail(
            @SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
            Model model, @PathVariable Long boardNum) {

        Optional<Board> board = boardRepository.findByBoardManageSeq(boardNum);
        if (board.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("sessionForm", sessionForm);


        Board findBoard = board.get();
        model.addAttribute("board", findBoard);


        return "board/detailBoard";
    }

    @GetMapping("/board/field/{field}")
    public String fieldByBoard(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
                               @PathVariable String field,
                               Model model) {

        List<Board> findField = boardRepository.findByField(field);

        model.addAttribute("sessionForm", sessionForm);
        model.addAttribute("field", field);
        model.addAttribute("findField", findField);

        return "board/board";
    }

    // 앞으로 해야할 게시글 추가, 수정, 삭제
    @GetMapping("/board/addBoard")
    public String addBoard(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
                           @ModelAttribute("boardForm") BoardForm boardForm, Model model) {

        model.addAttribute("sessionForm", sessionForm);

        return "board/addBoard";
    }

    @PostMapping("/board/addBoard")
    public String addBoard(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
                           @Valid @ModelAttribute("boardForm") BoardForm boardForm,
                           BindingResult bindingResult, Model model) {

        // 검증
        if (bindingResult.hasErrors()) {
            model.addAttribute("boardForm", boardForm);
            return "board/addBoard";
        }

        // success logic
        Date date = new Date();
        String dateToStr = String.format("%1$tY-%1$tm-%1$td", date);

        Board board = boardRepository.boardSave(new Board(sessionForm.getMemberManageSeq(), boardForm.getTitle(), boardForm.getContent(), dateToStr, sessionForm.getNickName(), boardForm.getField()));

        return "redirect:/myPage";
    }

    @GetMapping("/editBoard")
    public String editBoard(Model model){
        return "board/editBoard";
    }

    @PostMapping("/editBoard/{boardManageSeq}")
    public String editBoard(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false)SessionForm sessionForm,
                            @Valid @ModelAttribute("boardForm")BoardForm boardForm,
                            @PathVariable Long boardManageSeq){

//        Long memberManageSeq = sessionForm.getMemberManageSeq();

        Optional<Board> updateBoard = boardRepository.findByBoardManageSeq(boardManageSeq);
        if (updateBoard.isEmpty()){
            return "redirect:/";
        }

        Board board = updateBoard.get();

        boardRepository.boardUpdateByBoardManageSeq(boardManageSeq, );


        return "board/editBoard";
    }

    @GetMapping("/remove/{boardManageSeq}")
    public String remove(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
            @PathVariable Long boardManageSeq) {

        Long memberManageSeq = sessionForm.getMemberManageSeq();
        Optional<Board> byBoardManageSeq = boardRepository.findByBoardManageSeq(boardManageSeq);

        if (byBoardManageSeq.isEmpty()){
            return "redirect:/";
        }

        Board board = byBoardManageSeq.get();

        if (board.getWriterManageSeq() == memberManageSeq){
            boardRepository.removeBoardByBoardManage(boardManageSeq);
        }
        return "redirect:/";
    }


    public List<Board> extractAll(List<Board> list) {
        return list;
    }
}

