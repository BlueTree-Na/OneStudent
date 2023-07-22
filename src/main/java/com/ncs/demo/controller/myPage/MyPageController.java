package com.ncs.demo.controller.myPage;

import com.ncs.demo.config.session.SessionConst;
import com.ncs.demo.config.session.SessionForm;
import com.ncs.demo.domain.board.Board;
import com.ncs.demo.repository.boardRepository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final BoardRepository boardRepository;

    @GetMapping("/myPage")
    public String viewMyPage(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
                         Model model){

        List<Board> boardAll = boardRepository.findByWriterManageSeq(sessionForm.getMemberManageSeq());

        model.addAttribute("boardAll", boardAll);
        model.addAttribute("sessionForm", sessionForm);

        return "myPage/myPage";
    }

}
