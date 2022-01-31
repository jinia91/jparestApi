package jpabook.pracjpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
// @setter x 값타입이므로 불변객체
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // 불변객체 첫 생성
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    // 닫아놓기
    protected Address() {

    }
}
