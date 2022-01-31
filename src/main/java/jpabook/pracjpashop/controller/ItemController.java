package jpabook.pracjpashop.controller;

import jpabook.pracjpashop.dto.BookDto;
import jpabook.pracjpashop.domain.item.Book;
import jpabook.pracjpashop.domain.item.Item;
import jpabook.pracjpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {


    private  final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookDto());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookDto bookDto){
        Book book = new Book();
        book.setPrice(bookDto.getPrice());
        book.setName(bookDto.getName());
        book.setStockQuantity(bookDto.getStockQuantity());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findAllItem();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Book one = (Book) itemService.findOne(itemId);

        BookDto bookDto = new BookDto();
        bookDto.setAuthor(one.getAuthor());
        bookDto.setIsbn(one.getIsbn());
        bookDto.setName(one.getName());
        bookDto.setPrice(one.getPrice());
        bookDto.setStockQuantity(one.getStockQuantity());
        bookDto.setId(one.getId());

        model.addAttribute("form", bookDto);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookDto form){
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

}
