package peaksoft.dto.cheque.response;

import lombok.Builder;

import java.util.List;

/**
 * @author kurstan
 * @created at 25.03.2023 11:19
 */
@Builder
public record ChequePagination(
        List<ChequeResponse> cheques,
        int currentPage,
        int totalPages
) {
}
