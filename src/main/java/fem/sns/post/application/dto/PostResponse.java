package fem.sns.post.application.dto;

import fem.sns.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private Long memberId;
    private String contents;
    private LocalDate createDate;
    private LocalDateTime createAt;
}
