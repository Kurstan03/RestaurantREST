package peaksoft.dto.user.response;

import lombok.Builder;

/**
 * @author kurstan
 * @created at 16.03.2023 20:46
 */
@Builder
public record UserTokenResponse(
        String login,
        String token
) {
}
