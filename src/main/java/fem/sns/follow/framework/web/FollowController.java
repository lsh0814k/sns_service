package fem.sns.follow.framework.web;

import fem.sns.application.usecase.CreateFollowMemberUsecase;
import fem.sns.application.usecase.GetFollowingMembersUsecase;
import fem.sns.follow.framework.web.response.FollowMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {
    private final CreateFollowMemberUsecase createFollowMemberUsecase;
    private final GetFollowingMembersUsecase getFollowingMembersUsecase;

    @PostMapping("/{fromId}/{toId}")
    public void create(@PathVariable Long fromId, @PathVariable Long toId) {
        createFollowMemberUsecase.execute(fromId, toId);
    }

    @GetMapping("/members/{fromId}")
    public List<FollowMemberResponse> getFollowMembers(@PathVariable Long fromId) {
        return getFollowingMembersUsecase.execute(fromId);
    }
}
