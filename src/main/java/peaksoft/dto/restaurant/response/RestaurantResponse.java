package peaksoft.dto.restaurant.response;

/**
 * @author kurstan
 * @created at 17.03.2023 18:54
 */
public record RestaurantResponse(
        Long id,
        String name,
        String location,
        String restType,
        int numberOfEmployees,
        int service
) {
}
