package fem.sns.member.framework.web;

import fem.sns.member.application.MemberReadService;
import fem.sns.member.application.MemberWriteService;
import fem.sns.member.domain.Member;
import fem.sns.member.framework.web.request.RegisterMemberRequest;
import fem.sns.member.framework.web.response.MemberNicknameHistoryResponse;
import fem.sns.member.framework.web.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{id}")
    public MemberResponse changeNickname(@PathVariable Long id, @RequestBody String nickname) {
        Member member = memberWriteService.changeNickname(id, nickname);
        return MemberResponse.from(member);
    }

    @GetMapping("/{memberId}/nicname-histories")
    public List<MemberNicknameHistoryResponse> getNicknameHistories(@PathVariable Long memberId) {
        return memberReadService.getNicknameHistories(memberId)
                .stream()
                .map(MemberNicknameHistoryResponse::from)
                .toList();
    }
}
