package jpabook.pracjpashop.controller;

import jpabook.pracjpashop.domain.Address;
import jpabook.pracjpashop.domain.Member;
import jpabook.pracjpashop.dto.MemberForm;
import jpabook.pracjpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){

        model.addAttribute(new MemberForm());

        return "members/createMemberForm";

    }

    @PostMapping("/members/new")
    public String create(MemberForm memberForm){
        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        Long join = memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> all = memberService.findAll();
        model.addAttribute("members", all);
        return "members/memberList";
    }

}
