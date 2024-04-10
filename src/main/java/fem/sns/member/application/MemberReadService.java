package fem.sns.member.application;

import fem.sns.member.application.port.input.MemberNicknameHistoryRepository;
import fem.sns.member.application.port.input.MemberRepository;
import fem.sns.member.domain.Member;
import fem.sns.member.domain.MemberNicknameHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberReadService {
    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public Member getMember(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }

    public List<MemberNicknameHistory> getNicknameHistories(Long memberId) {
        return memberNicknameHistoryRepository.findAllByMemberId(memberId);
    }
}
