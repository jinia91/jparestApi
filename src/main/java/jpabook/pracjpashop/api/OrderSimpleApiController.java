package jpabook.pracjpashop.api;

import jpabook.pracjpashop.dto.OrderDto;
import jpabook.pracjpashop.repository.OrderSearch;
import jpabook.pracjpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Many to One, One to One(X to One, no Collection)
 * Order,
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderService orderService;

    @GetMapping("/api/v1/simple-orders")
    public ResponseEntity<List<OrderDto>> orderV1(){
        List<OrderDto> orderDtos = orderService.findAllByMemberDelivery()
                .stream().map(order -> OrderDto.builder()
                        .id(order.getId())
                        .localDateTime(order.getOrderDate())
                        .memberName(order.getMember().getName())
                        .address(order.getDelivery().getAddress())
                        .status(order.getStatus())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDtos);
    }

    // Repository에서 Dto로 바로 조회
    // 계층 데이터 운반을 위한 dto가 레포지토리단까지 접근하는것은 바람직하지 않아보임
    // 약간의 성능향상을 위해 레이어를 깨는 설계보다는 차라리 v1을 쓰자
    // 정말 많은 트래픽, 반드시 성능개선이 필요한 부분에서는 계층을 깨고 dto로 데이터를 퍼오는것도 고려해볼만함, trade - off
    @GetMapping("/api/v2/simple-orders")
    public ResponseEntity<List<OrderDto>> orderV2(){
        List<OrderDto> orderDtos = orderService.findAllDtoByMemberDelivery();
        return ResponseEntity.ok(orderDtos);
    }

}
