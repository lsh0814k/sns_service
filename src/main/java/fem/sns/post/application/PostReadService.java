package fem.sns.post.application;

import fem.sns.post.application.dto.DailyPostCount;
import fem.sns.post.application.port.input.PostRepository;
import fem.sns.post.application.dto.DailyPostCountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReadService {
    private final PostRepository postRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest dailyPostCountRequest) {
        return postRepository.groupByCreatedDate(dailyPostCountRequest);
    }
}
