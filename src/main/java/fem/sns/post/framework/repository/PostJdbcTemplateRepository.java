package fem.sns.post.framework.repository;

import fem.sns.post.application.dto.DailyPostCount;
import fem.sns.post.application.dto.DailyPostCountRequest;
import fem.sns.post.application.dto.PostResponse;
import fem.sns.post.application.port.input.PostRepository;
import fem.sns.post.domain.Post;
import fem.sns.post.framework.util.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
public class PostJdbcTemplateRepository implements PostRepository {
    private static final String TABLE = "Post";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final RowMapper<DailyPostCount> DAILY_POST_COUNT_ROW_MAPPER = (resultset, num) ->
            new DailyPostCount(
                    resultset.getLong("memberId"),
                    resultset.getObject("createDate", LocalDate.class),
                    resultset.getLong("count"));
    private static final RowMapper<PostResponse> POST_RESPONSE_ROW_MAPPER = (resultSet, num) -> PostResponse.builder()
            .id(resultSet.getLong("id"))
            .memberId(resultSet.getLong("memberId"))
            .contents(resultSet.getString("contents"))
            .createAt(resultSet.getObject("createAt", LocalDateTime.class))
            .createDate(resultSet.getObject("createDate", LocalDate.class))
            .build();

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

    @Override
    public Page<PostResponse> findAllByMemberIdAndOrderByIdDesc(Long memberId, Pageable pageable) {
        String sql = String.format(
                "select * " +
                "from %s " +
                "where memberId = :memberId " +
                "order by %s " +
                "limit :size " +
                "offset :offset ", TABLE, PageHelper.orderBy(pageable.getSort()));
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        List<PostResponse> posts = namedParameterJdbcTemplate.query(sql, params, POST_RESPONSE_ROW_MAPPER);
        return new PageImpl<>(posts, pageable, getCount(memberId));
    }

    @Override
    public List<PostResponse> findAllByMemberIdAndOrderByIdDesc(Long memberId, long size) {
        String sql = String.format(
                "select * " +
                "from %s " +
                "where memberId = :memberId " +
                "order by id desc " +
                "limit :size ", TABLE);
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, POST_RESPONSE_ROW_MAPPER);
    }

    @Override
    public List<PostResponse> findAllByLessThanIdAndMemberIdAndOrderByIdDesc(Long id, Long memberId, long size) {
        String sql = String.format(
                "select * " +
                "from %s " +
                "where memberId = :memberId " +
                "and id < :id " +
                "order by id desc " +
                "limit :size ", TABLE);
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("id", id)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, POST_RESPONSE_ROW_MAPPER);
    }

    @Override
    public List<PostResponse> findAllByInMemberIdAndOrderByIdDesc(List<Long> memberIds, long size) {
        if (memberIds == null || memberIds.isEmpty()) {
            return List.of();
        }

        String sql = String.format(
                "select * " +
                "from %s " +
                "where memberId in  (:memberIds) " +
                "order by id desc " +
                "limit :size ", TABLE);
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberIds", memberIds)
                .addValue("size", size);
        return namedParameterJdbcTemplate.query(sql, params, POST_RESPONSE_ROW_MAPPER);
    }

    @Override
    public List<PostResponse> findAllByLessThanIdAndInMemberIdAndOrderByIdDesc(Long id, List<Long> memberIds, long size) {
        if (memberIds == null || memberIds.isEmpty()) {
            return List.of();
        }

        String sql = String.format(
                "select * " +
                "from %s " +
                "where memberId in (:memberIds) " +
                "and id < :id " +
                "order by id desc " +
                "limit :size ", TABLE);
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberIds", memberIds)
                .addValue("id", id)
                .addValue("size", size);
        return namedParameterJdbcTemplate.query(sql, params, POST_RESPONSE_ROW_MAPPER);
    }

    public List<PostResponse> findAllByInId(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        String sql = String.format(
                "select * " +
                "from %s " +
                "where id in (:ids) ", TABLE);
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("ids", ids);
        return namedParameterJdbcTemplate.query(sql, params, POST_RESPONSE_ROW_MAPPER);
    }

    private Long getCount(Long memberId) {
        String sql = String.format(
                "select count(id) " +
                "from %s " +
                "where memberId = :memberId", TABLE);
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
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
