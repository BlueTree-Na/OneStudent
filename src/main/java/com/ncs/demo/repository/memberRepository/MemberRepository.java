package com.ncs.demo.repository.memberRepository;

import com.ncs.demo.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    // 회원 저장
    Member memberSave(Member member);

    // 로그인 id를 통해 회원 찾기
    Optional<Member> findById(String id);

    // MemberManageSeq로 회원 찾기
    Optional<Member> findByMemberManageSeq(Long memberManageSeq);

    // 모든 회원 찾기
    List<Member> findAllMember();

    // 별명으로 회원 찾기
    Optional<Member> findByNickName(String nickName);

    // 메모리 리셋
    default void clearStore(){};


}
