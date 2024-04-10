package fem.sns.follow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Follow {
    private Long id;
    private Long fromMemberId;
    private Long toMemberId;
    private LocalDateTime createAt;

    public static Follow create(Long fromMemberId, Long toMemberId) {
        return Follow.builder()
                .toMemberId(toMemberId)
                .fromMemberId(fromMemberId)
                .createAt(LocalDateTime.now())
                .build();
    }
}
