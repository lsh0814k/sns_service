package fem.sns.post.application.port.input;

import fem.sns.post.application.dto.DailyPostCount;
import fem.sns.post.application.dto.DailyPostCountRequest;
import fem.sns.post.application.dto.PostResponse;
import fem.sns.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepository {
    Post save(Post post);

    List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest dailyPostCountRequest);

    void bulkInsert(List<Post> posts);

    Page<PostResponse> findAllByMemberIdAndOrderByIdDesc(Long memberId, Pageable pageable);

    List<PostResponse> findAllByMemberIdAndOrderByIdDesc(Long memberId, long size);

    List<PostResponse> findAllByLessThanIdAndMemberIdAndOrderByIdDesc(Long id, Long memberId, long size);

    List<PostResponse> findAllByInMemberIdAndOrderByIdDesc(List<Long> memberIds, long size);

    List<PostResponse> findAllByLessThanIdAndInMemberIdAndOrderByIdDesc(Long id, List<Long> memberIds, long size);
}
