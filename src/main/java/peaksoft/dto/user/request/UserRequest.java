package peaksoft.dto.user.request;

import lombok.Builder;

/**
 * @author kurstan
 * @created at 16.03.2023 20:48
 */
@Builder
public record UserRequest(
        String login,
        String password
) {
}
