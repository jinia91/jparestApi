package jpabook.pracjpashop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookDto {

    private Long id;
    private String name;
    private int stockQuantity;
    private int price;
    private String author;
    private String isbn;



}
