package jpabook.pracjpashop.repository;

import jpabook.pracjpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class ItemRepositoryOld {

    private final EntityManager em;

    public void saveItem(Item item) {

        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {

        return em.find(Item.class, id);

    }

    public List<Item> findAll() {

        return em.createQuery("select i from Item i",Item.class).getResultList();

    }

}
