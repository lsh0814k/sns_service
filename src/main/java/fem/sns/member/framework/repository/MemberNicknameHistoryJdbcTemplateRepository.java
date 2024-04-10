package fem.sns.member.framework.repository;

import fem.sns.member.application.port.input.MemberNicknameHistoryRepository;
import fem.sns.member.domain.Member;
import fem.sns.member.domain.MemberNicknameHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberNicknameHistoryJdbcTemplateRepository implements MemberNicknameHistoryRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String TABLE = "MemberNicknameHistory";

    @Override
    public MemberNicknameHistory save(MemberNicknameHistory memberNicknameHistory) {
        if (memberNicknameHistory.getId() == null) {
            return insert(memberNicknameHistory);
        }

        throw new UnsupportedOperationException("MemberNicknameHistory 는 갱신을 지원하지 않습니다.");
    }

    @Override
    public List<MemberNicknameHistory> findAllByMemberId(Long memberId) {
        String sql = String.format("select * from %s where memberId = :memberId", TABLE);
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("memberId", memberId);
        RowMapper<MemberNicknameHistory> rowMapper = (resultSet, rownum) ->
                MemberNicknameHistory.builder()
                        .id(resultSet.getLong("id"))
                        .memberId(resultSet.getLong("memberId"))
                        .nickname(resultSet.getString("nickname"))
                        .createAt(resultSet.getObject("createAt", LocalDateTime.class))
                        .build();

        return namedParameterJdbcTemplate.query(sql, param, rowMapper);
    }

    private MemberNicknameHistory insert(MemberNicknameHistory history) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(history);
        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return MemberNicknameHistory.builder()
                .id(id)
                .nickname(history.getNickname())
                .memberId(history.getMemberId())
                .createAt(history.getCreateAt())
                .build();
    }
}
