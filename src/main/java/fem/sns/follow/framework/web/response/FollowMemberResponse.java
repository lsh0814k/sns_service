package fem.sns.follow.framework.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FollowMemberResponse {
    private Long memberId;
    private String nickname;
}
