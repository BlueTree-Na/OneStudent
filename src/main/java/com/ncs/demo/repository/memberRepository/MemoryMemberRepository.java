package com.ncs.demo.repository.memberRepository;

import com.ncs.demo.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> memberStore = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    @Override
    public Member memberSave(Member member) {
        member.setMemberManageSeq(sequence++);
        memberStore.put(member.getMemberManageSeq(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(String id) {
        return findAllMember().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Member> findByMemberManageSeq(Long memberManageSeq) {
        return findAllMember().stream()
                .filter(m -> m.getMemberManageSeq().equals(memberManageSeq))
                .findFirst();
    }

    @Override
    public List<Member> findAllMember() {
        return new ArrayList<>(memberStore.values());
    }

    @Override
    public Optional<Member> findByNickName(String nickName) {
        return findAllMember().stream()
                .filter(m -> m.getNickName().equals(nickName))
                .findFirst();
    }

    @Override
    public void clearStore() {
        memberStore.clear();
    }
}
