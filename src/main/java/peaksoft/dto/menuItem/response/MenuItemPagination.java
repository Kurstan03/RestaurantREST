package peaksoft.dto.menuItem.response;

import java.util.List;

/**
 * @author kurstan
 * @created at 25.03.2023 13:25
 */
public record MenuItemPagination(
        List<MenuItemResponse> menuItems,
        int currentPage,
        int pageSize
) {
}
