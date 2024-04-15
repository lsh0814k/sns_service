package fem.sns.timeline.framework.repository;

import fem.sns.timeline.application.port.input.TimelineRepository;
import fem.sns.timeline.domain.Timeline;
import io.micrometer.observation.annotation.Observed;
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
public class TimelineJdbcTemplateRepository implements TimelineRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String TABLE = "Timeline";

    private static final RowMapper<Timeline> TIMELINE_ROW_MAPPER = (resultset, num) ->
            Timeline.builder()
                    .id(resultset.getLong("id"))
                    .postId(resultset.getLong("postId"))
                    .memberId(resultset.getLong("memberId"))
                    .createAt(resultset.getObject("createAt", LocalDateTime.class))
                    .build();


    @Override
    public Timeline save(Timeline timeline) {
         if (timeline.getId() == null) {
             return insert(timeline);
         }

        throw new UnsupportedOperationException("Timeline 은 갱신을 지원하지 않습니다.");
    }

    @Observed
    public List<Timeline> findAllByLessThanIdAndMemberIdAndOrderByIdDesc(Long id, Long memberId, long size) {
        String sql = String.format(
                "select * " +
                "from %s " +
                "where memberId = :memberId " +
                "and id < :id " +
                "order by id desc " +
                "limit :size", TABLE);
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("memberId", memberId)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, TIMELINE_ROW_MAPPER);
    }

    @Override
    public List<Timeline> findAllByMemberIdAndOrderByDesc(Long memberId, long size) {
        String sql = String.format(
                "select * " +
                "from %s " +
                "where memberId = :memberId " +
                "order by id desc " +
                "limit :size", TABLE);
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, TIMELINE_ROW_MAPPER);
    }

    public void bulkInsert(List<Timeline> timelines) {
        String sql = String.format(
                "insert into %s (memberId, postId, createAt) " +
                "values (:memberId, :postId, :createAt) ", TABLE
        );

        SqlParameterSource[] params = timelines.stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    private Timeline insert(Timeline timeline) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withCatalogName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(timeline);
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return Timeline.builder()
                .id(id)
                .memberId(timeline.getMemberId())
                .postId(timeline.getPostId())
                .createAt(timeline.getCreateAt())
                .build();
    }
}
