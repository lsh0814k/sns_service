package fem.sns.post.framework.web;

import fem.sns.application.usecase.CreatePostUsecase;
import fem.sns.application.usecase.GetTimelinePostUsecase;
import fem.sns.post.application.PostReadService;
import fem.sns.post.application.PostWriteService;
import fem.sns.post.application.dto.*;
import fem.sns.post.framework.web.request.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final CreatePostUsecase createPostUsecase;
    private final PostWriteService postWriteService;
    private final PostReadService postReadService;
    private final GetTimelinePostUsecase getTimelinePostUsecase;
    @PostMapping
    public Long create(PostRequest postRequest) {
        return createPostUsecase.execute(postRequest.toModel());
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }

    @GetMapping("/{memberId}")
    public Page<PostResponse> getPosts(@PathVariable Long memberId, Pageable pageable) {
        return postReadService.getPosts(memberId, pageable);
    }

    @GetMapping("/{memberId}/by-cursor")
    public CursorResponse<PostResponse> getPostsByCursor(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("/member/{memberId}/timeline")
    public CursorResponse<PostResponse> getTimeline(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ) {
        return getTimelinePostUsecase.executeByTimeline(memberId, cursorRequest);
    }

}
