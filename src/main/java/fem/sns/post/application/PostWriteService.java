package fem.sns.post.application;

import fem.sns.post.application.port.input.PostRepository;
import fem.sns.post.domain.Post;
import fem.sns.post.domain.PostCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostWriteService {
    private final PostRepository postRepository;

    public Long create(PostCreate postCreate) {
        return postRepository.save(Post.create(postCreate)).getId();
    }
}
