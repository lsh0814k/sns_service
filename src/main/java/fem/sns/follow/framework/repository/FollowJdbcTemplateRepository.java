package fem.sns.follow.framework.repository;

import fem.sns.follow.application.port.input.FollowRepository;
import fem.sns.follow.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowJdbcTemplateRepository implements FollowRepository {
    private static final String TABLE = "Follow";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Follow save(Follow follow) {
        if (follow.getId() == null) {
            return insert(follow);
        }

        throw new UnsupportedOperationException("Follow 는 갱신을 지원하지 않습니다");
    }

    @Override
    public List<Follow> findAllByFromMemberId(Long fromMemberId) {
        String sql = "select * from Follow where fromMemberId = :fromMemberId";
        SqlParameterSource params = new MapSqlParameterSource().addValue("fromMemberId", fromMemberId);
        RowMapper<Follow> rowMapper = (resultSet, rowNum) ->
                Follow.builder()
                        .id(resultSet.getLong("id"))
                        .fromMemberId(resultSet.getLong("fromMemberId"))
                        .toMemberId(resultSet.getLong("toMemberId"))
                        .createAt(resultSet.getObject("createAt", LocalDateTime.class))
                    .build();
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    private Follow insert(Follow follow) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(follow);
        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return Follow.builder()
                .id(id)
                .fromMemberId(follow.getFromMemberId())
                .toMemberId(follow.getToMemberId())
                .createAt(follow.getCreateAt())
                .build();
    }


}
