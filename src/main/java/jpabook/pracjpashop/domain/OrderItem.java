package jpabook.pracjpashop.domain;

import jpabook.pracjpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Table(name = "order_item")
@Entity
@Getter @Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_order_id")
    private Order order;

    private int orderPrice;
    private int count;

    protected OrderItem(){}

    // 생성 메소드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;

    }


// 비지니스 로직
    public void cancel() {

        getItem().addStock(count);

    }

    public int getTotalPrice() {

        return getOrderPrice()*getCount();

    }
}