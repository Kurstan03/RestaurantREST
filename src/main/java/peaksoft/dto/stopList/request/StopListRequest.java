package peaksoft.dto.stopList.request;

import java.time.LocalDate;

/**
 * @author kurstan
 * @created at 20.03.2023 10:34
 */
public record StopListRequest(
        String reason,
        LocalDate date
) {
}
