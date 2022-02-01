package jpabook.pracjpashop.api;

import jpabook.pracjpashop.domain.Order;
import jpabook.pracjpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    /**
     * 페치조인
     */
    @GetMapping("/api/v3/orders")
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


    /**
     *
     * @Transactional(readOnly=true), 혹은 애너테이션 없음
     */
    @GetMapping("/api/v1/orders")
    public ResponseEntity<List<OrderDtoV2>> getOrdersV1(){
        List<Order> allByItemList = orderService.findAllByItemListNotFetchV1();
        List<OrderDtoV2> orderDtos = getDtos(allByItemList);
        return ResponseEntity.ok(orderDtos);
    }

    /**
     *
     * @Transactional(readOnly=false), @Transactional
     */
    @GetMapping("/api/v2/orders")
    public ResponseEntity<List<OrderDtoV2>> getOrdersV2(){
        List<Order> allByItemList = orderService.findAllByItemListNotFetchV2();
        List<OrderDtoV2> orderDtos = getDtos(allByItemList);
        return ResponseEntity.ok(orderDtos);
    }

    private List<OrderDtoV2> getDtos(List<Order> allByItemList) {
        return allByItemList
                .stream()
                .map(order -> OrderDtoV2.builder()
                        .id(order.getId())
                        .localDateTime(order.getOrderDate())
                        .status(order.getStatus())
                        .itemList(order.getOrderItemList().stream()
                                .map(orderItem ->
                                        new OrderItemDto(orderItem.getOrderPrice(), orderItem.getCount(), orderItem.getItem().getName()))
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

}
