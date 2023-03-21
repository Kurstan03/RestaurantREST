package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.dto.restaurant.response.RestaurantResponse;
import peaksoft.entities.Restaurant;

import java.util.List;
import java.util.Map;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    RestaurantResponse getRestaurantById(Long id);
}