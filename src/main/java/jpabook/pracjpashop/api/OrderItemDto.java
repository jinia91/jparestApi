package jpabook.pracjpashop.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemDto {

    private int orderPrice;
    private int count;
    private String itemName;
}
