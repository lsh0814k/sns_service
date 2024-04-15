package fem.sns.timeline.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Timeline {
    private Long id;
    private Long memberId;
    private Long postId;
    private LocalDateTime createAt;

    public static Timeline create(Long postId, Long memberId) {
        return Timeline.builder()
                .memberId(memberId)
                .postId(postId)
                .createAt(LocalDateTime.now())
                .build();
    }
}
