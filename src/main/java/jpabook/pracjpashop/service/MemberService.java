package jpabook.pracjpashop.service;

import jpabook.pracjpashop.domain.Address;
import jpabook.pracjpashop.domain.Member;
import jpabook.pracjpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    public Long join(Member member) {

        validateDuplicateMember(member);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();

    }

    // 회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findOne(Long id) {
        Optional<Member> foundMemberById = memberRepository.findById(id);
        return foundMemberById.get();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> foundMembers = memberRepository.findByName(member.getName());
        if (!foundMembers.isEmpty()) throw new IllegalArgumentException("이미 존재하는 회원");
    }

//    @PostConstruct
    private void buildData(){
        Member member1 = new Member();
        member1.setName("testA");
        member1.setAddress(new Address("a","b","c"));
        Member member2 = new Member();
        member2.setName("testB");
        member2.setAddress(new Address("a","b","c"));
        Member member3 = new Member();
        member3.setName("testC");
        member3.setAddress(new Address("a","b","c"));

        join(member1);
        join(member2);
        join(member3);

    }

    @Transactional
    public void editMember(Long memberId, String name) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        memberOptional.ifPresent(member -> member.setName(name));
    }
}
