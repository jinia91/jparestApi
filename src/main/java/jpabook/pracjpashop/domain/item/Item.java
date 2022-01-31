package jpabook.pracjpashop.domain.item;

import jpabook.pracjpashop.domain.Category;
import jpabook.pracjpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "item")
@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

//    비지니스 로직
    /*
    * 재고 증가
    * */
    public void addStock(int quantity){
        stockQuantity += quantity;
    }

    /*
     * 재고 감소
     * */
    public void removeStock(int quantity){
        int resultQuantity = stockQuantity - quantity;

        if(resultQuantity<0){
            throw new NotEnoughStockException("need more stock");
        }
        else{
            stockQuantity = resultQuantity;
        }

    }
}