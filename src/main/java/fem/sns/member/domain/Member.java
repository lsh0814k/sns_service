package fem.sns.member.domain;

import lombok.*;

import java.time.LocalDate;

import static lombok.AccessLevel.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Member {
    private Long id;
    private String nickname;
    private String email;
    private LocalDate birthDay;

    public static Member create(Register register) {
        return Member.builder()
                .email(register.getEmail())
                .nickname(register.getNickname())
                .birthDay(register.getBirthday())
                .build();
    }
}
