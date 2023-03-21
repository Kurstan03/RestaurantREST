package peaksoft.dto.stopList.response;

import java.time.LocalDate;

/**
 * @author kurstan
 * @created at 21.03.2023 10:10
 */
public record StopListResponse(
        Long id,
        String reason,
        LocalDate date
) {
}
