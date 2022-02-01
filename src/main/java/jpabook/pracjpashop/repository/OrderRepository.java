package jpabook.pracjpashop.repository;

import jpabook.pracjpashop.domain.Order;
import jpabook.pracjpashop.dto.OrderDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o " +
            "from  Order o " +
            "join fetch o.member m " +
            "join  fetch o.delivery d")
    List<Order> findAllWithMemberDelivery();

    // 페치는 컬럼을 조작해서 jpql -> sql로 번역하기때문에 컬럼을 지정할때는 join을 쓰기
    @Query("select new jpabook.pracjpashop.dto.OrderDto(o.id, m.name, o.orderDate, d.address, o.status)  " +
            "from  Order o " +
            "join o.member m " +
            "join o.delivery d")
    List<OrderDto> findAllDtoWithMemberDelivery();

    @Query("select distinct o " +
            "from Order o " +
            "join fetch o.member m " +
            "join fetch o.delivery d " +
            "join fetch  o.orderItemList il " +
            "join fetch il.item i ")
    List<Order> findAllByItemList();


}
