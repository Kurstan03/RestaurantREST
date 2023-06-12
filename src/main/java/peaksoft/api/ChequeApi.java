package peaksoft.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.cheque.request.ChequeRequest;
import peaksoft.dto.cheque.response.ChequePagination;
import peaksoft.dto.cheque.response.ChequeResponse;
import peaksoft.dto.cheque.response.SumAllChequeOfDay;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author kurstan
 * @created at 19.03.2023 14:22
 */
@RestController
@RequestMapping("/api/{userId}/cheques")
public class ChequeApi {
    private final ChequeService chequeService;

    public ChequeApi(ChequeService chequeService) {
        this.chequeService = chequeService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    ChequePagination getAll(@PathVariable Long userId,
                            @RequestParam(required = false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "4") int size){
        return chequeService.getAll(userId, PageRequest.of(page - 1, size));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    SimpleResponse createCheque(@PathVariable Long userId,
                                @RequestBody ChequeRequest chequeRequest){
        return chequeService.createCheque(userId, chequeRequest);
    }
    @PutMapping("/{chequeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    SimpleResponse update(@PathVariable Long userId,
                          @PathVariable Long chequeId,
                          @RequestBody ChequeRequest chequeRequest){
        return chequeService.update(chequeId, chequeRequest);
    }
    @DeleteMapping("/{chequeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    SimpleResponse delete(@PathVariable Long userId,
                          @PathVariable Long chequeId){
        return chequeService.delete(chequeId);
    }

    @GetMapping("/{chequeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    ChequeResponse getById(@PathVariable Long userId,
                           @PathVariable Long chequeId){
        return chequeService.getById(chequeId);
    }
    @GetMapping("/sumAllChequeOfDay")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    SumAllChequeOfDay sumAllChequeOfDay(@PathVariable Long userId,
                                        @RequestParam LocalDate date){
        return chequeService.sumAllChequeOfDay(userId, date);
    }
    @GetMapping("/avgChequeSum")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    SimpleResponse avgCheque(@PathVariable Long userId,
                             @RequestParam LocalDate date){
        return chequeService.avgCheque(userId, date);
    }
}
