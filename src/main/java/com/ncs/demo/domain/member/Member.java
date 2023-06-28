package com.ncs.demo.domain.member;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Getter @Setter
public class Member {

    private Long memberManageSeq; // 회원 관리 번호

    @NotEmpty
    @NotNull
    private String id; // 회원 id

    @NotEmpty
    @NotNull
    private String pw; // 회원 pw

    @NotEmpty
    @NotNull
    private String name; // 회원 이름

    @NotEmpty
    @NotNull
    private String nickName; // 회원 별명

    public Member(String id, String pw, String name, String nickName) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.nickName = nickName;
    }
}
