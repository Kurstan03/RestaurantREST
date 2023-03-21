package peaksoft.dto.cheque.response;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import peaksoft.dto.menuItem.response.MenuItemResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author kurstan
 * @created at 19.03.2023 14:41
 */
@Getter
@Setter
public class ChequeResponse {
    private Long id;
    private LocalDate date;
    private String waiter;
    private List<MenuItemForCheque> meals;
    private BigDecimal averagePrice;
    private int service;
    private BigDecimal grandTotal;

    public ChequeResponse(Long id, LocalDate date, String waiter, BigDecimal averagePrice, int service) {
        this.id = id;
        this.date = date;
        this.waiter = waiter;
        this.averagePrice = averagePrice;
        this.service = service;
    }
}
