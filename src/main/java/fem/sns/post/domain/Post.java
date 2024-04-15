package fem.sns.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Post {
    private Long id;
    private Long memberId;
    private String contents;
    private LocalDate createDate;
    private LocalDateTime createAt;
    private long likeCount;

    public static Post create(PostCreate postCreate) {
        return Post.builder()
                .memberId(postCreate.getMemberId())
                .contents(postCreate.getContents())
                .likeCount(0)
                .createAt(LocalDateTime.now())
                .createDate(LocalDate.now())
                .build();
    }

    public void incrementLikeCount() {
        likeCount += 1;
    }

}
