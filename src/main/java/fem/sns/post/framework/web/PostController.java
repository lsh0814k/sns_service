package fem.sns.post.framework.web;

import fem.sns.post.application.PostReadService;
import fem.sns.post.application.PostWriteService;
import fem.sns.post.application.dto.DailyPostCount;
import fem.sns.post.application.dto.DailyPostCountRequest;
import fem.sns.post.framework.web.request.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostWriteService postWriteService;
    private final PostReadService postReadService;
    @PostMapping
    public Long create(PostRequest postRequest) {
        return postWriteService.create(postRequest.toModel());
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }
}
