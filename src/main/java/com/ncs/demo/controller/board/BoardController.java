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

    @GetMapping("/board/addBoard")
    public String addBoard(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
                           @ModelAttribute("boardForm") BoardForm boardForm,
                           Model model) {

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

    @GetMapping("/edit/{boardManageSeq}")
    public String editBoard(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false)SessionForm sessionForm,
                            @PathVariable Long boardManageSeq,
                            Model model){

        model.addAttribute("sessionForm", sessionForm);
        Optional<Board> byBoardManageSeq = boardRepository.findByBoardManageSeq(boardManageSeq);
        if (byBoardManageSeq.isEmpty()){
            return "redirect:/";
        }

        Board board = byBoardManageSeq.get();

        model.addAttribute("board", board);
        model.addAttribute("boardForm", new BoardForm(board.getField(), board.getTitle(), board.getContent()));

        return "board/editBoard";
    }

    // BindingResult객체는 반드시 @ModelAttribute 바로 옆에 작성한다!!
    // 안하면 BindingResult 가 동작 안해서 매핑 자체 오류 발생
    @PostMapping("/edit/{boardManageSeq}")
    public String editBoard(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false)SessionForm sessionForm,
                            @Valid @ModelAttribute("boardForm")BoardForm boardForm,
                            BindingResult bindingResult,
                            @PathVariable Long boardManageSeq,
                            Model model){

        model.addAttribute("sessionForm", sessionForm);
        Optional<Board> byBoardManageSeq = boardRepository.findByBoardManageSeq(boardManageSeq);
        if (byBoardManageSeq.isEmpty()){
            return "redirect:/";
        }

        Board board = byBoardManageSeq.get();

        log.info("{}", board.getField());
        log.info("{}", board.getTitle());
        log.info("{}", board.getContent());
        log.info("{}", boardForm);

        if (bindingResult.hasErrors()){
            boardForm.setField(board.getField());
            boardForm.setTitle(board.getTitle());
            boardForm.setContent(board.getContent());
            model.addAttribute("board", board);
            return "board/editBoard";
        }

        Date date = new Date();
        String dateToStr = String.format("%1$tY-%1$tm-%1$td", date);

        Board updatedBoard = new Board(sessionForm.getMemberManageSeq(), boardForm.getTitle(), boardForm.getContent(), dateToStr, sessionForm.getNickName(), boardForm.getField());

        boardRepository.boardUpdateByBoardManageSeq(boardManageSeq, updatedBoard);

        return "redirect:/myPage";
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

