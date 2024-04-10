package fem.sns.member.framework.web.response;

import fem.sns.member.domain.Member;
import fem.sns.member.domain.MemberNicknameHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MemberNicknameHistoryResponse {
    private Long id;
    private Long memberId;
    private String nickname;
    private LocalDateTime createAt;

    public static MemberNicknameHistoryResponse from(MemberNicknameHistory history) {
        return MemberNicknameHistoryResponse.builder()
                .id(history.getId())
                .memberId(history.getMemberId())
                .nickname(history.getNickname())
                .createAt(history.getCreateAt())
                .build();
    }
}
