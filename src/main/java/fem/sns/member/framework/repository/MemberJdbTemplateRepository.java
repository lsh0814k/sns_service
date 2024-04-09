package fem.sns.member.framework.repository;

import fem.sns.member.application.port.input.MemberRepository;
import fem.sns.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberJdbTemplateRepository implements MemberRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final static String TABLE = "Member";
    @Override
    public Member save(Member member) {
        if (member.getId() == null) {
            return insert(member);
        } else {
            return update(member);
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = String.format("select * from %s where id = :id", TABLE);
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        RowMapper<Member> rowMapper = (resultSet, rownum) ->
                Member.builder()
                    .id(resultSet.getLong("id"))
                    .email(resultSet.getString("email"))
                    .nickname(resultSet.getString("nickname"))
                    .birthDay(resultSet.getObject("birthday", LocalDate.class))
                    .build();
        Member member = namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper);

        return Optional.ofNullable(member);
    }

    private Member insert(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("Member")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);

        Number key = simpleJdbcInsert.executeAndReturnKey(params);

        return Member.builder()
                .id(key.longValue())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthDay(member.getBirthDay())
                .build();
    }

    private Member update(Member member) {
        return member;
    }
}
