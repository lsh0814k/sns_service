package fem.sns.post.framework.web;

import fem.sns.post.application.PostWriteService;
import fem.sns.post.framework.web.request.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostWriteService postWriteService;

    @PostMapping
    public Long create(PostRequest postRequest) {
        return postWriteService.create(postRequest.toModel());
    }
}
