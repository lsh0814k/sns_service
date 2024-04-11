package fem.sns.application.usecase;

import fem.sns.follow.application.FollowReadService;
import fem.sns.follow.domain.Follow;
import fem.sns.post.application.PostReadService;
import fem.sns.post.application.dto.CursorRequest;
import fem.sns.post.application.dto.CursorResponse;
import fem.sns.post.application.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTimelinePostUsecase {
    private final PostReadService postReadService;
    private final FollowReadService followReadService;
    public CursorResponse<PostResponse> execute(Long memberId, CursorRequest cursorRequest) {
        List<Follow> followings = followReadService.getFollowings(memberId);
        List<Long> followingMemberIds = followings.stream()
                .map(Follow::getToMemberId)
                .toList();
        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }
}
