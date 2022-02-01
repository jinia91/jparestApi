package jpabook.pracjpashop.api;

import jpabook.pracjpashop.domain.Member;
import jpabook.pracjpashop.dto.*;
import jpabook.pracjpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("api/v1/members")
    public ResponseEntity<CreateMemberResponse> saveMember(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.getName());
        Long join = memberService.join(member);
        return ResponseEntity
                .ok(new CreateMemberResponse(join));
    }

    @PatchMapping("api/v1/members/{memberId}")
    public ResponseEntity<UpdateMemberResponse> patchMember(@RequestBody @Valid UpdateMemberRequest request,
                                            @PathVariable("memberId") Long memberId) {

        memberService.editMember(memberId, request.getName());
        Member findMember = memberService.findOne(memberId);
        return ResponseEntity
                .ok(new UpdateMemberResponse(findMember.getId(), findMember.getName()));
    }

    @GetMapping("api/v1/members")
    public ResponseEntity<Object> getMembers() {
        List<MemberDto> memberDtos =
                memberService.findAll()
                        .stream()
                        .map(member -> new MemberDto(member.getName()))
                        .collect(Collectors.toList());

        Map<String,Object> result = new HashMap<>();
        result.put("members", memberDtos);
        result.put("Count", memberDtos.size());

        return ResponseEntity
                .ok(result);
    }
}
