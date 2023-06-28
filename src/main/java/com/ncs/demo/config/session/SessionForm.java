package com.ncs.demo.config.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data @Getter @Setter
public  class SessionForm {

    private String id;
    private String nickName;
    private Long memberManageSeq;

}
