package fem.sns.post.application;

import fem.sns.post.application.dto.*;
import fem.sns.post.application.port.input.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.OptionalLong;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReadService {
    private final PostRepository postRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest dailyPostCountRequest) {
        return postRepository.groupByCreatedDate(dailyPostCountRequest);
    }

    public Page<PostResponse> getPosts(Long memberId, Pageable pageable) {
        return postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, pageable);
    }

    public CursorResponse<PostResponse> getPosts(Long memberId, CursorRequest cursorRequest) {
        List<PostResponse> posts = findAllBy(memberId, cursorRequest);
        long nextKey = posts.stream()
                .mapToLong(PostResponse::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);

        return new CursorResponse<>(cursorRequest.next(nextKey), posts);
    }

    private List<PostResponse> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        } else {
            return postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
        }
    }
}
