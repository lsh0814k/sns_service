package fem.sns.member.application.port.input;

import fem.sns.member.domain.MemberNicknameHistory;

import java.util.List;

public interface MemberNicknameHistoryRepository {
    MemberNicknameHistory save(MemberNicknameHistory history);

    List<MemberNicknameHistory> findAllByMemberId(Long memberId);

}
