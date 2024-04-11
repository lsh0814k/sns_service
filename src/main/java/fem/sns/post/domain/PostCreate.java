package fem.sns.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostCreate {
    private Long memberId;
    private String contents;
}
