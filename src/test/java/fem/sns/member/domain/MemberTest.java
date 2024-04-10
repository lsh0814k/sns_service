package fem.sns.member.domain;

import fem.sns.util.MemberFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @Test
    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    void changeNickname() {
        // given
        Member member = MemberFixtureFactory.create();
        String expected = "lee";

        // when
        member.changeNickname(expected);

        // then
        assertThat(expected).isEqualTo(member.getNickname());
    }

    @Test
    @DisplayName("닉네임은 최대 10자까지 입력할 수 있다.")
    void nicknameMaxLength() {
        // given
        Member member = MemberFixtureFactory.create();
        String overMaxLengthNickname = "12345678901234";

        // when
        // then
        assertThatThrownBy(() -> member.changeNickname(overMaxLengthNickname))
                .isInstanceOf(IllegalArgumentException.class);
    }
}