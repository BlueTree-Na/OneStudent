package com.ncs.demo.controller.board;

import com.ncs.demo.config.session.SessionConst;
import com.ncs.demo.config.session.SessionForm;
import com.ncs.demo.domain.board.Board;
import com.ncs.demo.repository.boardRepository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

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
                               Model model){

        List<Board> findField = boardRepository.findByField(field);

        model.addAttribute("sessionForm", sessionForm);
        model.addAttribute("field", field);
        model.addAttribute("findField", findField);

        return "board/board";
    }

    // 앞으로 해야할 게시글 추가, 수정, 삭제
    //    @GetMapping("/addBoard")
//    public String addBoard(Model model){
//        return "board/addBoard";
//    }

//    @GetMapping("/editBoard")
//    public String editBoard(Model model){
//        return "board/editBoard";
//    }

    public List<Board> extractAll(List<Board> list) {
        return list;
    }

}
