package peaksoft.dto.cheque.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author kurstan
 * @created at 19.03.2023 16:24
 */

@Getter
@Setter
public class SumAllChequeOfDay {
    private String waiter;
    private LocalDate date;
    private Long counterOfCheque;
    private BigDecimal totalSumma;

    public SumAllChequeOfDay(String waiter, LocalDate date) {
        this.waiter = waiter;
        this.date = date;
    }

    public SumAllChequeOfDay() {

    }
}
