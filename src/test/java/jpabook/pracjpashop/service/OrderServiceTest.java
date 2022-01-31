package jpabook.pracjpashop.service;

import jpabook.pracjpashop.domain.*;
import jpabook.pracjpashop.exception.NotEnoughStockException;
import jpabook.pracjpashop.domain.item.Book;
import jpabook.pracjpashop.domain.item.Item;
import jpabook.pracjpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    EntityManager em;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = new Member();
        member.setName("테스트회원1");
        member.setAddress(new Address("a","b","c"));
        em.persist(member);

        Item book = new Book();
        book.setName("책");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order one = orderRepository.findOne(orderId);
        assertThat(one.getStatus()).isEqualTo(OrderStatus.ORDER)
                .withFailMessage("상태는 주문이여야함");
        assertThat(one.getOrderItemList().size())
                .isEqualTo(1)
                .withFailMessage("주문한 상품수가 정확해야한다.");
        assertThat(one.getTotalPrice())
                .isEqualTo(2000*orderCount);

    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = new Member();
        member.setName("테스트회원1");
        member.setAddress(new Address("a","b","c"));
        em.persist(member);

        Item book = new Book();
        book.setName("책");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        orderService.cancelOrder(orderId);




        // then
        Order one = orderRepository.findOne(orderId);
        assertThat(one.getStatus()).isEqualTo(OrderStatus.CANCEL)
                .withFailMessage("상태는 취소가 되어야함");
    }

    @Test
    public void 재고수량초과() throws Exception {
        // given
        Member member = new Member();
        member.setName("테스트회원1");
        member.setAddress(new Address("a","b","c"));
        em.persist(member);

        Item book = new Book();
        book.setName("책");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);


        // when
        int orderCount = 12;

        assertThrows(NotEnoughStockException.class,
                ()-> orderService.order(member.getId(), book.getId(), orderCount));

        // then

        assertThat(book.getStockQuantity()).isEqualTo(10);


    }

}