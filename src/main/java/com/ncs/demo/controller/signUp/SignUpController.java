package com.ncs.demo.controller.signUp;

import com.ncs.demo.domain.member.Member;
import com.ncs.demo.repository.memberRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SignUpController {

    private final MemberRepository memberRepository;

    @GetMapping("/signUp")
    public String signUp(@ModelAttribute("signUpForm") SignUpForm signUpForm){
        return "signUp/signUp";
    }


    @PostMapping("/signUp")
    public String signUpAdd(@Valid @ModelAttribute("signUpForm") SignUpForm signUpForm
            , BindingResult bindingResult){

        // 에러
        if (bindingResult.hasErrors()){
            return "signUp/signUp";
        }

        // 중복 검사
        Optional<Member> findNickName = memberRepository.findByNickName(signUpForm.getNickName());
        if (findNickName.isPresent()){
            log.info("signUp log : 이미 존재하는 nickName:{}", signUpForm.getNickName());
            bindingResult.addError(new FieldError("signUpForm", "nickName", "nickName이 이미 존재합니다."));
            return "signUp/signUp";
        }

        // 중복 검사
        Optional<Member> member = memberRepository.findById(signUpForm.getId());
        if (member.isPresent()){
            log.info("signUp log : 이미 존재하는 ID:{}", signUpForm.getId());
            bindingResult.addError(new FieldError("signUpForm", "id", "id가 이미 존재합니다."));
            return "signUp/signUp";
        }

        // Success Logic
        Member saveMember = new Member(signUpForm.getId(),
                signUpForm.getPw(),
                signUpForm.getName(),
                signUpForm.getNickName());
        memberRepository.memberSave(saveMember);

        return "redirect:/login";
    }

}
