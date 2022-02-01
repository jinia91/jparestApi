package jpabook.pracjpashop.api;

import jpabook.pracjpashop.domain.Order;
import jpabook.pracjpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @GetMapping("/api/v1/orders")
    public ResponseEntity<List<OrderDtoV2>> getOrders(){
        List<Order> allByItemList = orderService.findAllByItemList();
        List<OrderDtoV2> orderDtos = allByItemList.stream()
                .map(order -> OrderDtoV2.builder()
                        .id(order.getId())
                        .memberName(order.getMember().getName())
                        .localDateTime(order.getOrderDate())
                        .address(order.getDelivery().getAddress())
                        .status(order.getStatus())
                        .itemList(order.getOrderItemList().stream()
                                .map(orderItem ->
                                        new OrderItemDto(orderItem.getOrderPrice(),orderItem.getCount(),orderItem.getItem().getName()))
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderDtos);
    }

    @GetMapping("/api/v2/orders")
    public ResponseEntity<List<OrderDtoV2>> getOrders2(){
        List<Order> allByItemList = orderService.findAllByItemListNotFetch();
        List<OrderDtoV2> orderDtos = allByItemList
                .stream()
                .map(order -> OrderDtoV2.builder()
                        .id(order.getId())
                        .localDateTime(order.getOrderDate())
                        .status(order.getStatus())
                        .itemList(order.getOrderItemList().stream()
                                .map(orderItem ->
                                        new OrderItemDto(orderItem.getOrderPrice(),orderItem.getCount(),orderItem.getItem().getName()))
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDtos);
    }


}
