package com.ncs.demo.controller.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class BoardForm {
    @NotEmpty
    @NotNull
    @NotBlank
    private String field;
    @NotEmpty
    @NotNull
    @NotBlank
    private String title;
    @NotEmpty
    @NotNull
    @NotBlank
    private String content;
}
