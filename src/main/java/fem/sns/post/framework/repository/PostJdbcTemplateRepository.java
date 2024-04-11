package fem.sns.post.framework.repository;

import fem.sns.post.application.dto.DailyPostCount;
import fem.sns.post.application.dto.DailyPostCountRequest;
import fem.sns.post.application.port.input.PostRepository;
import fem.sns.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostJdbcTemplateRepository implements PostRepository {
    private static final String TABLE = "Post";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final RowMapper<DailyPostCount> DAILY_POST_COUNT_ROW_MAPPER = (resultset, num) ->
            new DailyPostCount(
                    resultset.getLong("memberId"),
                    resultset.getObject("createDate", LocalDate.class),
                    resultset.getLong("count"));
    @Override
    public Post save(Post post) {
        if (post.getId() == null) {
            return insert(post);
        }

        throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다.");
    }

    @Override
    public List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest request) {
        String sql = String.format(
                "select createDate, memberId, count(id) as count " +
                "from %s " +
                "where memberId = :memberId " +
                "and createDate between :firstDate and :lastDate " +
                "group by createDate, memberId", TABLE);
        SqlParameterSource params = new BeanPropertySqlParameterSource(request);
        return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_ROW_MAPPER);
    }

    @Override
    public void bulkInsert(List<Post> posts) {
        String sql = String.format(
                "insert into %s (memberId, contents, createDate, createAt) " +
                        "values (:memberId, :contents, :createDate, :createAt)", TABLE);
        SqlParameterSource[] params = posts.stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    private Post insert(Post post) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createDate(post.getCreateDate())
                .createAt(post.getCreateAt())
                .build();
    }
}
