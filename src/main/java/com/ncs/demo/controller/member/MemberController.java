package com.ncs.demo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @GetMapping("signUp/signUp")
    public String saveForm(){
        return "signUp/signUp";
    }

    @PostMapping("signUp/save")
    public String save() {

        return "signUp";
    }
}
