package jpabook.pracjpashop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberDto {
    private String name;
    private List<String> orderList;

    public MemberDto(String name) {
        this.name = name;
    }
}
