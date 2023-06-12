package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.restaurant.request.RestaurantRequest;
import peaksoft.dto.restaurant.response.RestaurantResponse;
import peaksoft.service.RestaurantService;

/**
 * @author kurstan
 * @created at 17.03.2023 18:47
 */
@RestController
@RequestMapping("/api/restaurant")
@Tag(name = "Restaurant api")
public class RestaurantApi {
    private final RestaurantService restaurantService;

    public RestaurantApi(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    RestaurantResponse restaurant(@PathVariable Long restaurantId){
        return restaurantService.getRestaurant(restaurantId);
    }

    @Operation(summary = "This save Restaurant", description = "this method has validation")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse save(@RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.save(restaurantRequest);
    }
    @PutMapping("/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse update(@PathVariable Long restaurantId,
                          @RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.update(restaurantId, restaurantRequest);
    }
    @DeleteMapping("/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse delete(@PathVariable Long restaurantId){
        return restaurantService.delete(restaurantId);
    }

}
