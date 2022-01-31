package jpabook.pracjpashop.repository;

import jpabook.pracjpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

//@Repository
@RequiredArgsConstructor
public class MemberRepositoryOLD {

    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {

        return em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    public List<Member> findName(String name) {
        return em.createQuery("select m from Member m where m.name=:name", Member.class)
                .setParameter("name", name).getResultList();
    }


}
