//package jpabook.pracjpashop.service;
//
//import com.querydsl.core.QueryResults;
//import com.querydsl.core.Tuple;
//import com.querydsl.jpa.JPAExpressions;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jpabook.pracjpashop.domain.*;
//import jpabook.pracjpashop.domain.item.Book;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//import java.util.List;
//
//import static com.querydsl.jpa.JPAExpressions.*;
//import static jpabook.pracjpashop.domain.QMember.*;
//import static jpabook.pracjpashop.domain.QMember.member;
//import static jpabook.pracjpashop.domain.QOrder.order;
//import static jpabook.pracjpashop.domain.QOrderItem.*;
//import static jpabook.pracjpashop.domain.QTeam.*;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//@Rollback(value = false)
//public class QueryDslTest {
//
//    @Autowired
//    EntityManager em;
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    OrderService orderService;
//    @Autowired
//    ItemService itemService;
//
//    JPAQueryFactory queryDsl;
//
//
//    @BeforeEach
//    public void before() {
//
//
//        Team teamA = new Team("teamA");
//        Team teamB = new Team("teamB");
//        em.persist(teamA);
//        em.persist(teamB);
//        Member member1 = new Member();
//        member1.setName("testA");
//        member1.setTeam(teamA);
//        em.persist(member1);
//
//        Book book = new Book();
//
//        book.setStockQuantity(100);
//
//        itemService.saveItem(book);
//
//
//        orderService.order(member1.getId(), book.getId(), 1);
//
//
//        Member member2 = new Member();
//        member2.setName("testB");
//        member2.setTeam(teamA);
//        Member member3 = new Member();
//        member3.setName("testC");
//        member3.setTeam(teamA);
//        Member member4 = new Member();
//        member4.setName("testD");
//        member4.setTeam(teamB);
//        Member member5 = new Member();
//        member5.setTeam(teamB);
//        Member member6 = new Member();
//        member6.setName("teamA");
//        member6.setTeam(teamB);
//        em.persist(member2);
//        em.persist(member3);
//        em.persist(member4);
//        em.persist(member5);
//        em.persist(member6);
//
//
//        queryDsl = new JPAQueryFactory(em);
//
//    }
//
//
//    @Test
//    public void 쿼리dsl시작() throws Exception {
//        // given
//        Member memberRs = em.createQuery(
//                        "select m " +
//                                "from Member m " +
//                                "where m.name = :name"
//                        , Member.class)
//                .setParameter("name", "testA")
//                .getSingleResult();
//
//        JPAQueryFactory queryDsl = new JPAQueryFactory(em);
//
//        Member memberQRs = queryDsl
//                .select(member)
//                .from(member)
//                .where(member.name.eq("testA"))
//                .fetchOne();
//        // when
//
//        // then
//
//        assertThat(memberQRs).isEqualTo(memberRs);
//    }
//
//    @Test
//    public void 쿼리체인() throws Exception {
//        // given
//        JPAQueryFactory queryDsl = new JPAQueryFactory(em);
//
//        Member memberQRs = queryDsl
//                .select(member)
//                .from(member)
//                .where(member.name.eq("testB")
//                        .and(member.id.eq(2L)))
//                .fetchOne();
//
//        // when
//
//        // then
//    }
//
//    @Test
//    public void 검색() throws Exception {
//        // given
//
//        // when
//
//        List<Member> foundMembers = queryDsl
//                .selectFrom(member)
//                .where(member.id.loe(10L))
//                .orderBy(member.name.desc().nullsFirst())
//                .fetch();
//
//
//        // then
//
//        System.out.println("===================================================");
//
//        System.out.println(foundMembers.size());
//
//        for (Member foundMember : foundMembers) {
//
//            System.out.println("foundMember = " + foundMember.getName() + foundMember.getId());
//
//        }
//
//
//        System.out.println("===================================================");
//
//
//    }
//
//    @Test
//    public void 페이징() throws Exception {
//        // given
//        QueryResults<Member> memberQueryResults = queryDsl
//                .select(member)
//                .from(member)
//                .leftJoin(member.team, team)
//                .orderBy(member.name.desc())
//                .offset(1)
//                .limit(2)
//                .fetchResults();
//
//        // count(*)
//        assertThat(memberQueryResults.getTotal()).isEqualTo(9L);
//
//
//    }
//
//
//    @Test
//    public void 튜플집합() throws Exception {
//        // given
//
//        em.clear();
//
//        Tuple tuple = queryDsl.select(
//                        member.name,
//                        member.id.max()
//                ).from(member)
//                .fetchOne();
//
//
//        // then
//
//        assertThat(tuple.get(member.id.max())).isEqualTo(9L);
//    }
//
//
//    @Test
//    public void 조인테스트() throws Exception {
//        // given
//        // 팀 A에 소속된 모든 회원
//
//        // when
//
//        List<Member> teamA = queryDsl
//                .select(member)
//                .from(member)
//                .join(member.team, team)
//                .where(team.name.eq("teamA"))
//                .fetch();
//
//        // then
//
//        assertThat(teamA.size()).isEqualTo(3);
//
//        assertThat(teamA)
//                .extracting("name")
//                .contains("testA", "testB", "testC");
//
//    }
//
//    @Test
//    public void 세타조인() throws Exception {
//        // given
//        // 회원 이름이 팀이름과 같은 회원 조회
//        // when
//
//        em.clear();
//
//        Member member = queryDsl.select(QMember.member)
//                .from(QMember.member, team)
//                .where(QMember.member.name.eq(team.name))
//                .fetchOne();
//
//        // then
//
//
//    }
//
//    @Test
//    public void 조인필터링() throws Exception {
//        // given
//        //회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
//
//        em.clear();
//        System.out.println("=========================================");
//        // when
//        List<Tuple> teamA = queryDsl.select(member.name, team)
//                .from(member)
////                .leftJoin(team)       // 카티션곱 크로스조인
//                .leftJoin(member.team, team)
//                .on(team.name.eq("teamA"))
//                .orderBy(member.name.desc().nullsFirst())
//                .fetch();
//        // then
//
//        for (Tuple tuple : teamA) {
//
//            System.out.println("tuple = " + tuple);
//
//        }
//
//    }
//
//    @Test
//    public void 페치조인() throws Exception {
//        // given
//        em.clear();
//
//        // 페치조인 안할때
//        Member member1 = queryDsl
//                .selectFrom(member)
//                .where(member.name.eq("testD"))
//                .fetchOne();
//
//        em.clear();
//        //페치조인할때
//        List<Member> testD = queryDsl
//                .selectFrom(member)
//                .join(member.team, team).fetchJoin()
//                .where(member.name.eq("testD"))
//                .fetch();
//
//
//        // when
//
//        // then
//    }
//
//
//    @Test
//    public void 서브쿼리() throws Exception {
//        // given
//        QMember memberSub = new QMember("sub");
//
//        Member member1 = queryDsl
//                .select(member)
//                .from(member)
//                .join(member.team, team).fetchJoin()
//                .where(member.name.eq(
//                                 select(memberSub.name)
//                                .from(memberSub)
//                                .where(memberSub.name.eq("testD"))
//                ))
//                .fetchOne();
//        // then
//        assertThat(member1)
//                .isNotNull()
//                .extracting(Member::getName).isEqualTo("testD");
//
//    }
//
//
//}
