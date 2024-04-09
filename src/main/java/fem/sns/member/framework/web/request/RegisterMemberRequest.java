package fem.sns.member.framework.web.request;

import fem.sns.member.domain.Register;

import java.time.LocalDate;

public record RegisterMemberRequest(
        String email,
        String nickname,
        LocalDate birthday
){
    public Register toModel() {
        return Register.builder()
                .email(this.email)
                .nickname(this.nickname)
                .birthday(this.birthday)
                .birthday(this.birthday)
                .build();
    }
}