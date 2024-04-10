package fem.sns.member.application;

import fem.sns.member.application.port.input.MemberRepository;
import fem.sns.member.domain.Member;
import fem.sns.member.domain.Register;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberWriteService {
    private final MemberRepository memberRepository;
    public void register(Register register) {
        memberRepository.save(Member.create(register));
    }

    public Member changeNickname(Long memberId, String nickname) {
        Member member = memberRepository.getById(memberId);
        member.changeNickname(nickname);
        memberRepository.save(member);

        return member;
    }
}
