package fem.sns.timeline.application;

import fem.sns.post.application.dto.CursorRequest;
import fem.sns.post.application.dto.CursorResponse;
import fem.sns.timeline.application.port.input.TimelineRepository;
import fem.sns.timeline.domain.Timeline;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TimelineReadService {
    private final TimelineRepository timelineRepository;

    public CursorResponse<Timeline> getTimelines(Long memberId, CursorRequest cursorRequest) {
        List<Timeline> timelines = findAllBy(memberId, cursorRequest);
        long nextKey = timelines.stream()
                .mapToLong(Timeline::getId)
                .min().orElse(CursorRequest.NONE_KEY);
        return new CursorResponse<>(cursorRequest.next(nextKey), timelines);
    }

    private List<Timeline> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return timelineRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        }

        return timelineRepository.findAllByMemberIdAndOrderByDesc(memberId, cursorRequest.size());
    }
}
