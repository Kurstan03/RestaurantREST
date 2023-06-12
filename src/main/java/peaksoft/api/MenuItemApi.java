package peaksoft.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.menuItem.request.MenuItemRequest;
import peaksoft.dto.menuItem.response.MenuItemResponse;
import peaksoft.dto.pagination.PaginationResponse;
import peaksoft.service.MenuItemService;

import java.util.List;

/**
 * @author kurstan
 * @created at 18.03.2023 15:15
 */
@RestController
@RequestMapping("/api/{subCategoryId}/menuItems")
public class MenuItemApi {
    private final MenuItemService menuItemService;

    public MenuItemApi(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<MenuItemResponse> getAll(@PathVariable Long subCategoryId,
                                  @RequestParam(required = false) String keyWord) {
        return menuItemService.getAll(subCategoryId, keyWord);
    }

    @PostMapping("/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse save(@PathVariable Long restaurantId,
                        @PathVariable Long subCategoryId,
                        @RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.save(restaurantId, subCategoryId, menuItemRequest);
    }

    @GetMapping("/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    MenuItemResponse getById(@PathVariable Long subCategoryId,
                             @PathVariable Long menuItemId) {
        return menuItemService.getById(menuItemId);
    }

    @PutMapping("/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse update(@PathVariable Long subCategoryId,
                          @PathVariable Long menuItemId,
                          @RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.update(menuItemId, menuItemRequest);
    }

    @DeleteMapping("/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse delete(@PathVariable Long subCategoryId,
                          @PathVariable Long menuItemId) {
        return menuItemService.delete(menuItemId);
    }

    @GetMapping("/sort")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<MenuItemResponse> sort(@PathVariable Long subCategoryId,
                                @RequestParam(required = false) String ascOrDesc) {
        return menuItemService.sort(ascOrDesc);
    }

    @GetMapping("/vegetarian")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<MenuItemResponse> isVegetarian(@PathVariable Long subCategoryId,
                                        @RequestParam boolean isTrue) {
        return menuItemService.isVegetarian(isTrue);
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    PaginationResponse<MenuItemResponse> pagination(@PathVariable Long subCategoryId,
                                                    @RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "4") int size) {
        return menuItemService.pagination(PageRequest.of(page - 1, size));
    }
}
