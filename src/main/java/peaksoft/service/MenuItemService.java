package peaksoft.service;

import org.springframework.data.domain.PageRequest;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.menuItem.request.MenuItemRequest;
import peaksoft.dto.menuItem.response.MenuItemResponse;
import peaksoft.dto.pagination.PaginationResponse;

import java.util.List;

/**
 * @author kurstan
 * @created at 18.03.2023 15:21
 */
public interface MenuItemService {
    List<MenuItemResponse> getAll(Long subCategoryId, String keyWord);

    SimpleResponse save(Long restaurantId, Long subCategoryId, MenuItemRequest menuItemRequest);

    SimpleResponse update(Long menuItemId, MenuItemRequest menuItemRequest);

    SimpleResponse delete(Long menuItemId);

    List<MenuItemResponse> sort(String ascOrDesc);

    List<MenuItemResponse> isVegetarian(boolean isTrue);

    MenuItemResponse getById(Long menuItemId);

    PaginationResponse<MenuItemResponse> pagination(PageRequest pageRequest);
}
