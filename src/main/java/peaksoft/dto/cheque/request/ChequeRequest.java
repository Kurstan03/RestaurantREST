package peaksoft.dto.cheque.request;

import java.time.LocalDate;
import java.util.List;

/**
 * @author kurstan
 * @created at 19.03.2023 15:31
 */
public record ChequeRequest(
        List<Long> mealsId
) {
}
