package peaksoft.dto.user.request;

/**
 * @author kurstan
 * @created at 17.03.2023 15:07
 */
public record AcceptOrRejectRequest(
        Long userId,
        boolean accept
) {
}
