package com.ncs.demo.controller.login;

import com.ncs.demo.config.session.SessionConst;
import com.ncs.demo.config.session.SessionForm;
import com.ncs.demo.domain.member.Member;
import com.ncs.demo.repository.memberRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm form) {
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm form,
                        BindingResult bindingResult,
                        HttpServletRequest request,
                        @RequestParam(defaultValue = "/") String redirectURI){


        if (bindingResult.hasErrors()){
            return "login/login";
        }

        // login service
        Optional<Member> member = memberRepository.findById(form.getId());
        if (member.isEmpty()){
            log.info("login log : 존재하지 않는 id 로그인 시도{}", form.getId());
            bindingResult.reject("loginFail", "로그인 실패");
            return "login/login";
        }

        Member findMember = member.get();
        if (!findMember.getPw().equals(form.getPw())){
            log.info("login log : 로그인 실패 id :{} , pw :{}",form.getId(), form.getPw());
            bindingResult.reject("loginFail", "아이디와 비밀번호가 일치하지 않습니다.");
            return "login/login";
        }

        // login Success logic
        HttpSession session = request.getSession();

        SessionForm sessionForm = new SessionForm(findMember.getId(), findMember.getNickName(), findMember.getMemberManageSeq());
        session.setAttribute(SessionConst.LOGIN_SESSION_KEY, sessionForm);
        return "redirect:"+redirectURI; // 로그인 필요한 작업시 현재 페이지 저장URL

    }

    // Post 쓰는 이유
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        return "redirect:/";
    }

}
