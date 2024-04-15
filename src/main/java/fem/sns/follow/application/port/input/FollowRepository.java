package fem.sns.follow.application.port.input;

import fem.sns.follow.domain.Follow;

import java.util.List;

public interface FollowRepository {
    Follow save(Follow follow);

    List<Follow> findAllByFromMemberId(Long fromMemberId);

    List<Follow> findAllByToMemberId(Long toMemberId);
}
