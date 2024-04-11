package fem.sns.post.application.dto;

import java.util.List;

public record CursorResponse<T>(
        CursorRequest nextCursorRequest,
        List<T> body
) {
}
