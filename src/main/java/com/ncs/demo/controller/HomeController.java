package com.ncs.demo.controller;

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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardRepository boardRepository;

    @GetMapping("/")
    public String home(
            @SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm
            , Model model) {

        // Success Logic
        List<Board> backField = boardRepository.findByField("Back-End");
        List<Board> frontField = boardRepository.findByField("Front-End");
        List<Board> aiField = boardRepository.findByField("AI");
        List<Board> dbField = boardRepository.findByField("DB");
        List<Board> algorithmField = boardRepository.findByField("Algorithm");


//        frontField.get(1).getWriterNickname()
        model.addAttribute("sessionForm", sessionForm);
        model.addAttribute("frontField", extractTwo(frontField));
        model.addAttribute("backField", extractTwo(backField));
        model.addAttribute("aiField", extractTwo(aiField));
        model.addAttribute("dbField", extractTwo(dbField));
        model.addAttribute("algorithmField", extractTwo(algorithmField));


        // 로그인 x 사용자
        if (sessionForm == null) {
            return "home/index";
        }

        return "home/loggedHome";
    }

    public List<Board> extractTwo(List<Board> list) {

        if (list.size() < 2) {
            return list;
        } else {
            ArrayList<Board> boards = new ArrayList<>();
            boards.add(list.get(list.size() - 2));
            boards.add(list.get(list.size() - 1));

            return list;
        }
    }

}
