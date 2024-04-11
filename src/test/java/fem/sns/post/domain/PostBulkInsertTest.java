package fem.sns.post.domain;

import fem.sns.post.application.port.input.PostRepository;
import fem.sns.util.PostFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
class PostBulkInsertTest {
    @Autowired private PostRepository postRepository;

    @Test
    void bulInsert() {
        EasyRandom easyRandom = PostFixtureFactory.get(
                2L,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Post> list = IntStream.range(0, 200000)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();
        stopWatch.stop();

        System.out.println("객체 생성 시간: " + stopWatch.getTotalTimeSeconds());

        StopWatch queryStopWatch = new StopWatch();
        queryStopWatch.start();
        postRepository.bulkInsert(list);
        queryStopWatch.stop();

        System.out.println("객체 생성 시간 : " + queryStopWatch.getTotalTimeSeconds());
    }
}