package fem.sns.application.usecase;

import fem.sns.follow.application.FollowReadService;
import fem.sns.follow.domain.Follow;
import fem.sns.post.application.PostWriteService;
import fem.sns.post.domain.PostCreate;
import fem.sns.timeline.application.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatePostUsecase {
    private final PostWriteService postWriteService;
    private final FollowReadService followReadService;
    private final TimelineWriteService timelineWriteService;

    public Long execute(PostCreate postCreate) {
        Long postId = postWriteService.create(postCreate);
        List<Long> followerMemberIds = followReadService.getFollowers(postCreate.getMemberId()).stream()
                .map(Follow::getFromMemberId)
                .toList();
        timelineWriteService.deliveryToTimeline(postId, followerMemberIds);

        return postId;
    }
}
