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
    private static final String TABLE = "Member";
    private static final RowMapper<Member> ROW_MAPPER = (resultSet, rownum) ->
            Member.builder()
                    .id(resultSet.getLong("id"))
                    .email(resultSet.getString("email"))
                    .nickname(resultSet.getString("nickname"))
                    .birthDay(resultSet.getObject("birthday", LocalDate.class))
                    .build();
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
        Member member = namedParameterJdbcTemplate.queryForObject(sql, params, ROW_MAPPER);

        return Optional.ofNullable(member);
    }

    @Override
    public Member getById(Long id) {
        Optional<Member> memberOptional = findById(id);
        return memberOptional.orElseThrow(() -> new IllegalArgumentException("잘못된 ID 입니다."));
    }

    @Override
    public List<Member> findAllByIdIn(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        String sql = String.format("select * from %s where id in (:ids)", TABLE);
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("ids", ids);

        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
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
        String sql = String.format("update %s set email = :email, nickname = :nickname, birthDay = :birthDay " +
                "where id = :id", TABLE);
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(sql, params);

        return member;
    }


}
