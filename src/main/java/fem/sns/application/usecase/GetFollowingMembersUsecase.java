package fem.sns.application.usecase;

import fem.sns.follow.application.FollowReadService;
import fem.sns.follow.domain.Follow;
import fem.sns.follow.framework.web.response.FollowMemberResponse;
import fem.sns.member.application.MemberReadService;
import fem.sns.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFollowingMembersUsecase {
    private final MemberReadService memberReadService;
    private final FollowReadService followReadService;
    public List<FollowMemberResponse> execute(Long fromId) {
        List<Follow> followings = followReadService.getFollowings(fromId);
        List<Member> members = memberReadService.findAllByIdIn(followings.stream().map(Follow::getToMemberId).toList());

        return members.stream()
                .map(item -> FollowMemberResponse.builder()
                        .memberId(item.getId())
                        .nickname(item.getNickname())
                        .build())
                .toList();
    }
}
