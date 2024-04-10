package fem.sns.member.application;

import fem.sns.member.application.port.input.MemberNicknameHistoryRepository;
import fem.sns.member.application.port.input.MemberRepository;
import fem.sns.member.domain.Member;
import fem.sns.member.domain.MemberNicknameHistory;
import fem.sns.member.domain.Register;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberWriteService {
    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;
    public void register(Register register) {
        Member member = memberRepository.save(Member.create(register));

        saveMemberNicknameHistory(member);
    }

    public Member changeNickname(Long memberId, String nickname) {
        Member member = memberRepository.getById(memberId);
        member.changeNickname(nickname);
        memberRepository.save(member);

        saveMemberNicknameHistory(member);

        return member;
    }

    private void saveMemberNicknameHistory(Member member) {
        MemberNicknameHistory history = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        memberNicknameHistoryRepository.save(history);
    }
}
