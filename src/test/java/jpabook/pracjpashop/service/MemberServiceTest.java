package jpabook.pracjpashop.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.pracjpashop.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager entityManager;


    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("테스트1");

        // when
        Long joinMemberId = memberService.join(member);

        // then
        Assertions.assertThat(member).isEqualTo(memberService.findOne(joinMemberId));
    }

    @Test
    public void 중복회원예외() throws Exception {

        // given
        Member member1 = new Member();
        member1.setName("김프붕");
        Member member2 = new Member();
        member2.setName("김프붕");


        // when

        memberService.join(member1);
        ;

        assertThrows(IllegalArgumentException.class, () -> memberService.join(member2));
        // then
    }


    @Test
    public void 플러시테스트() throws Exception {
        // given
        Team team = new Team("테스트팀");
        entityManager.persist(team);

        Member member = new Member();
        member.setName("테스트1");
        member.setTeam(team);

        // when
        Long joinMemberId = memberService.join(member);
        entityManager.flush();
        entityManager.clear();

        System.out.println("======================================================");

//        Member member2 = memberService.findOne(joinMemberId);

        Member member2 = entityManager.createQuery("select m from Member m " +
                        "join m.team " +
                        "where m.id = :id", Member.class)
                .setParameter("id", joinMemberId)
                .getSingleResult();
        System.out.println("======================================================");
        Team team2 = member2.getTeam();
        team2.getName();
        System.out.println("======================================================");
        List<Member> memberList = team2.getMemberList();
//        for (Member member1 : memberList) {
//            System.out.println("======================================================");
//            System.out.println("member1 = " + member1);
//        }
//        System.out.println("======================================================");

        // then

    }


    @Test
    public void 쿼리dsl사용테스트() throws Exception {
        // given
        Member member = new Member();
        member.setName("테스트1");
        Long joinMemberId = memberService.join(member);

        // when

        JPAQueryFactory query = new JPAQueryFactory(entityManager);
        QMember qMember = new QMember("testA");

        Member member1 = query
                .selectFrom(qMember)
                .fetchOne();


        // then

        Assertions.assertThat(member1).isEqualTo(member);

    }

}