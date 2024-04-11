package fem.sns.post.application.port.input;

import fem.sns.post.domain.Post;

public interface PostRepository {
    Post save(Post post);
}
