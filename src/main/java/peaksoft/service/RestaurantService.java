package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.restaurant.request.RestaurantRequest;
import peaksoft.dto.restaurant.response.RestaurantResponse;

/**
 * @author kurstan
 * @created at 17.03.2023 18:49
 */
public interface RestaurantService {

    RestaurantResponse getRestaurant(Long restaurantId);

    SimpleResponse save(RestaurantRequest restaurantRequest);

    SimpleResponse update(Long restaurantId, RestaurantRequest restaurantRequest);

    SimpleResponse delete(Long restaurantId);
}
