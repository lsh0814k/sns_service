package fem.sns.follow.application;

import fem.sns.follow.application.port.input.FollowRepository;
import fem.sns.follow.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowWriteService {
    private final FollowRepository followRepository;

    public Follow create(Long fromMemberId, Long toMemberId) {
        Follow follow = Follow.create(fromMemberId, toMemberId);
        return followRepository.save(follow);
    }
}
