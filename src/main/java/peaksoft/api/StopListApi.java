package peaksoft.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.stopList.request.StopListRequest;
import peaksoft.dto.stopList.response.StopListResponse;
import peaksoft.entities.StopList;
import peaksoft.service.StopListService;

import java.util.List;

/**
 * @author kurstan
 * @created at 20.03.2023 10:29
 */
@RestController
@RequestMapping("/api/{menuItemId}/stopLists")
public class StopListApi {
    private final StopListService stopListService;

    public StopListApi(StopListService stopListService) {
        this.stopListService = stopListService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<StopListResponse> getStopLists(@PathVariable Long menuItemId) {
        return stopListService.getStopLists(menuItemId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse create(@PathVariable Long menuItemId,
                          @RequestBody StopListRequest stopListRequest) {
        return stopListService.create(menuItemId, stopListRequest);
    }

    @PutMapping("/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse update(@PathVariable Long menuItemId,
                          @PathVariable Long stopListId,
                          @RequestBody StopListRequest stopListRequest) {
        return stopListService.update(menuItemId, stopListId, stopListRequest);
    }
    @DeleteMapping("/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse delete(@PathVariable Long menuItemId,
                          @PathVariable Long stopListId){
        return stopListService.delete(menuItemId, stopListId);
    }
}
