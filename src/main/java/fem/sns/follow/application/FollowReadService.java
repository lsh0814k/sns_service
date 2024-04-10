package fem.sns.follow.application;


import fem.sns.follow.application.port.input.FollowRepository;
import fem.sns.follow.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowReadService {
    private final FollowRepository followRepository;

    public List<Follow> getFollowings(Long fromMemberId) {
        return followRepository.findAllByFromMemberId(fromMemberId);
    }
}
