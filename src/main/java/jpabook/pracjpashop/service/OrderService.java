package jpabook.pracjpashop.service;

import jpabook.pracjpashop.domain.Delivery;
import jpabook.pracjpashop.domain.Member;
import jpabook.pracjpashop.domain.Order;
import jpabook.pracjpashop.domain.OrderItem;
import jpabook.pracjpashop.domain.item.Item;
import jpabook.pracjpashop.dto.OrderDto;
import jpabook.pracjpashop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepositoryOld orderRepositoryOld;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문
    public Long order(Long memberId, Long itemId, int count) {

        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findById(itemId).get();

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderItem);

        // 캐스캐이드로 내부에 위의 엔티티들 모두 영속화!
        orderRepository.save(order);

        return order.getId();
    }

    // 취소

    public void cancelOrder(Long orderId) {
        Optional<Order> orderOp = orderRepository.findById(orderId);
        orderOp.ifPresent(Order::cancel);
    }

    // 검색

    public List<Order> findByCriteria(OrderSearch orderSearch) {
        return orderRepositoryOld.findAllByCriteria(orderSearch);
    }

    // api service
    public List<Order> findAllByMemberDelivery(){
        return orderRepository.findAllWithMemberDelivery();
    }

    public List<OrderDto> findAllDtoByMemberDelivery(){
        return orderRepository.findAllDtoWithMemberDelivery();
    }

    public List<Order> findAllByItemList(){
        return orderRepository.findAllByItemList();
    }

    public List<Order> findAllByItemListNotFetch(){
        return orderRepositoryOld.findAllWithMemberDelivery(0,10);
    }


//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }


}
