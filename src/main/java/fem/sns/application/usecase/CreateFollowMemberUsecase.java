package fem.sns.application.usecase;

import fem.sns.follow.application.FollowWriteService;
import fem.sns.member.application.MemberReadService;
import fem.sns.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFollowMemberUsecase {
    private final MemberReadService memberReadService;
    private final FollowWriteService followWriteService;
    public void execute(Long fromMemberId, Long toMemberId) {
        Member fromMember = memberReadService.getMember(fromMemberId);
        Member toMember = memberReadService.getMember(toMemberId);

        followWriteService.create(fromMember.getId(), toMember.getId());
    }
}
