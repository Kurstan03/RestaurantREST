package peaksoft.dto.cheque.response;

import java.math.BigDecimal;

/**
 * @author kurstan
 * @created at 19.03.2023 14:43
 */
public record MenuItemForCheque(
        Long id,
        String name,
        BigDecimal price,
        Long amount
) {
}
