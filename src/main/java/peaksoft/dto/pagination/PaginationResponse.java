package peaksoft.dto.pagination;

import lombok.Builder;

import java.util.List;

/**
 * @author kurstan
 * @created at 26.03.2023 14:01
 */
@Builder
public record PaginationResponse<T>(
        List<T> elements,
        int currentPage,
        int totalPage
) {
}
