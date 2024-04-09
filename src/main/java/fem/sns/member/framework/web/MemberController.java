package fem.sns.member.framework.web;

import fem.sns.member.application.MemberReadService;
import fem.sns.member.application.MemberWriteService;
import fem.sns.member.domain.Member;
import fem.sns.member.framework.web.request.RegisterMemberRequest;
import fem.sns.member.framework.web.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;

    @PostMapping
    public void register(@RequestBody RegisterMemberRequest registerMemberRequest) {
        memberWriteService.register(registerMemberRequest.toModel());
    }

    @GetMapping("/{id}")
    public MemberResponse getMember(@PathVariable Long id) {
        Member member = memberReadService.getMember(id);
        return MemberResponse.from(member);
    }
}
