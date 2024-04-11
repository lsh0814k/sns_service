package fem.sns.post.application.dto;

import java.time.LocalDate;

public record DailyPostCount(Long memberId, LocalDate date, Long postCount) {
}
