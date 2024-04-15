package fem.sns.timeline.application.port.input;

import fem.sns.timeline.domain.Timeline;

import java.util.List;

public interface TimelineRepository {
    Timeline save(Timeline timeline);

    List<Timeline> findAllByLessThanIdAndMemberIdAndOrderByIdDesc(Long id, Long memberId, long size);

    List<Timeline> findAllByMemberIdAndOrderByDesc(Long memberId, long size);
    void bulkInsert(List<Timeline> timelines);
}
