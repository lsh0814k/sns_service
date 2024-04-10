package fem.sns.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter


@NoArgsConstructor(access = PROTECTED)
public class MemberNicknameHistory {
    private Long id;
    private Long memberId;
    private String nickname;
    private LocalDateTime createAt;

    @Builder
    public MemberNicknameHistory(Long id, Long memberId, String nickname, LocalDateTime createAt) {
        this.id = id;
        this.memberId = memberId;
        this.nickname = nickname;
        this.createAt = createAt == null ? LocalDateTime.now() : createAt;
    }
}
