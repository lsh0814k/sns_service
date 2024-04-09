package fem.sns.member.framework.web.response;

import fem.sns.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String nickname;
    private String email;
    private LocalDate birthday;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthDay())
                .build();
    }
}
