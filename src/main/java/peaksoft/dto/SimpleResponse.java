package peaksoft.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

/**
 * @author kurstan
 * @created at 17.03.2023 15:31
 */
@Builder
public record SimpleResponse(
        HttpStatus status,
        String description
) {
}
