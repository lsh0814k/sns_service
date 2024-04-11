package fem.sns.post.framework.web.request;

import fem.sns.post.domain.PostCreate;

public record PostRequest (Long memberId, String contents){
    public PostCreate toModel() {
        return PostCreate.builder()
                .memberId(memberId)
                .contents(contents)
                .build();
    }
}
