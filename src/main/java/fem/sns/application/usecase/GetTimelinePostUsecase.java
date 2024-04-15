package fem.sns.application.usecase;

import fem.sns.follow.application.FollowReadService;
import fem.sns.follow.domain.Follow;
import fem.sns.post.application.PostReadService;
import fem.sns.post.application.dto.CursorRequest;
import fem.sns.post.application.dto.CursorResponse;
import fem.sns.post.application.dto.PostResponse;
import fem.sns.timeline.application.TimelineReadService;
import fem.sns.timeline.domain.Timeline;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTimelinePostUsecase {
    private final PostReadService postReadService;
    private final FollowReadService followReadService;
    private final TimelineReadService timelineReadService;
    public CursorResponse<PostResponse> execute(Long memberId, CursorRequest cursorRequest) {
        List<Follow> followings = followReadService.getFollowings(memberId);
        List<Long> followingMemberIds = followings.stream()
                .map(Follow::getToMemberId)
                .toList();
        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }

    public CursorResponse<PostResponse> executeByTimeline(Long memberId, CursorRequest cursorRequest) {
        CursorResponse<Timeline> pagedTimelines = timelineReadService.getTimelines(memberId, cursorRequest);
        List<Long> postIds = pagedTimelines.body().stream().map(Timeline::getPostId).toList();
        List<PostResponse> posts = postReadService.getPosts(postIds);

        return new CursorResponse<>(pagedTimelines.nextCursorRequest(), posts);
    }
}
