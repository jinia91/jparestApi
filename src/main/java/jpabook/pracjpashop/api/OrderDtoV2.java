package jpabook.pracjpashop.api;

import jpabook.pracjpashop.domain.Address;
import jpabook.pracjpashop.domain.OrderItem;
import jpabook.pracjpashop.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OrderDtoV2 {

    private Long id;
    private String memberName;
    private LocalDateTime localDateTime;
    private Address address;
    private OrderStatus status;
    private List<OrderItemDto> itemList;
}
