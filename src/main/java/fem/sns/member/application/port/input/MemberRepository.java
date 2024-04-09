package fem.sns.member.application.port.input;

import fem.sns.member.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(Long id);
}
