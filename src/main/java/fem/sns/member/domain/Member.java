package fem.sns.member.domain;

import lombok.*;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Objects;

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
        validNickname(register.getNickname());
        return Member.builder()
                .email(register.getEmail())
                .nickname(register.getNickname())
                .birthDay(register.getBirthday())
                .build();
    }

    public void changeNickname(String nickname) {
        validNickname(nickname);
        this.nickname = nickname;
    }

    private static void validNickname(String nickname) {
        Objects.requireNonNull(nickname);
        Assert.isTrue(nickname.length() <= 10, "nickname의 최대 길이는 10자 입니다.");
    }
}
