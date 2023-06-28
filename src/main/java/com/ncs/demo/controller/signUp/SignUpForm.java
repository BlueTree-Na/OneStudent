package com.ncs.demo.controller.signUp;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SignUpForm {

    @NotEmpty
    @NotNull
    @NotBlank
    private String id;

    @NotEmpty
    @NotNull
    @NotBlank
    private String pw;

    @NotEmpty
    @NotNull
    @NotBlank
    private String name;

    @NotEmpty
    @NotNull
    @NotBlank
    private String nickName;

}
