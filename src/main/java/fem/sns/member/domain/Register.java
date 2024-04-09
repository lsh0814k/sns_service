package fem.sns.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class Register {
    private String email;
    private String nickname;
    private LocalDate birthday;
}
