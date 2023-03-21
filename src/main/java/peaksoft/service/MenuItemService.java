package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.menuItem.request.MenuItemRequest;
import peaksoft.dto.menuItem.response.MenuItemResponse;

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
}
