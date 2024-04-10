package fem.sns.follow.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FollowCreate {
    private Long fromMemberId;
    private Long toMemberId;
}
