package fem.sns.post.application.port.input;

import fem.sns.post.application.dto.DailyPostCount;
import fem.sns.post.application.dto.DailyPostCountRequest;
import fem.sns.post.domain.Post;

import java.util.List;

public interface PostRepository {
    Post save(Post post);

    List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest dailyPostCountRequest);
}
