package jpabook.pracjpashop.dto;

import jpabook.pracjpashop.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long id;
    private String memberName;
    private LocalDateTime localDateTime;
    private Address address;
    private OrderStatus status;
}
