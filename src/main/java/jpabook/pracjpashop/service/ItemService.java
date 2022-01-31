package jpabook.pracjpashop.service;

import jpabook.pracjpashop.domain.item.Book;
import jpabook.pracjpashop.domain.item.Item;
import jpabook.pracjpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Item item) {

        itemRepository.save(item);
    }

    public List<Item> findAllItem() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {

        return itemRepository.findById(id).get();
    }


    @PostConstruct
    private void buildData() {

        Book book1 = new Book();
        book1.setName("testBook");
        book1.setIsbn("sdf");
        book1.setPrice(5555);
        book1.setAuthor("sdfsdf");
        book1.setStockQuantity(1000);
        Book book2 = new Book();
        book2.setName("testBook2");
        book2.setIsbn("sdf");
        book2.setPrice(5255);
        book2.setAuthor("sdfsdf");
        book2.setStockQuantity(5000);

        saveItem(book1);
        saveItem(book2);

    }

}
