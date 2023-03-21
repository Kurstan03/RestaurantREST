package peaksoft.dto.menuItem.request;

import java.math.BigDecimal;

/**
 * @author kurstan
 * @created at 18.03.2023 15:32
 */
public record MenuItemRequest(
        String name,
        String image,
        BigDecimal price,
        String description,
        boolean isVegetarian
) {
}
