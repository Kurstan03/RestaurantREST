package peaksoft.dto.restaurant.request;

/**
 * @author kurstan
 * @created at 17.03.2023 19:20
 */
public record RestaurantRequest(
        String name,
        String location,
        String restType,
        int service
) {
}
