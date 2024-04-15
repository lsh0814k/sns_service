package fem.sns.timeline.application;

import fem.sns.timeline.application.port.input.TimelineRepository;
import fem.sns.timeline.domain.Timeline;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TimelineWriteService {
    private final TimelineRepository timelineRepository;

    public void deliveryToTimeline(Long postId, List<Long> toMemberIds) {
        List<Timeline> timelines = toMemberIds.stream()
                .map(memberId -> Timeline.create(postId, memberId))
                .toList();

        timelineRepository.bulkInsert(timelines);
    }

}
