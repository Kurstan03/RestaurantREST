package peaksoft.dto.menuItem.response;

import java.math.BigDecimal;

/**
 * @author kurstan
 * @created at 18.03.2023 15:24
 */
public record MenuItemResponse(
        Long id,
        String name,
        String image,
        BigDecimal price,
        String description,
        boolean isVegetarian
) {
}
