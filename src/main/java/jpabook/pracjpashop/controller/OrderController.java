package jpabook.pracjpashop.controller;

import jpabook.pracjpashop.domain.Member;
import jpabook.pracjpashop.domain.Order;
import jpabook.pracjpashop.domain.item.Item;
import jpabook.pracjpashop.repository.OrderSearch;
import jpabook.pracjpashop.service.ItemService;
import jpabook.pracjpashop.service.MemberService;
import jpabook.pracjpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){

        List<Member> all = memberService.findAll();
        List<Item> allItem = itemService.findAllItem();

        model.addAttribute("members", all);
        model.addAttribute("items",allItem);

        return "order/oderForm";

    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){
        orderService.order(memberId, itemId, count);

        return "redirect:/orders";

    }

    @GetMapping("/orders")
    public String oderList(@ModelAttribute("orderSearch")OrderSearch orderSearch,
                           Model model){
        List<Order> byCriteria = orderService.findByCriteria(orderSearch);
        model.addAttribute("orders",byCriteria);

        return "order/orderList";

    }
    @PostMapping(value = "/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }


}
