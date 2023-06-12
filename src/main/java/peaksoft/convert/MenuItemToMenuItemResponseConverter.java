package peaksoft.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import peaksoft.dto.menuItem.response.MenuItemResponse;
import peaksoft.entities.MenuItem;

/**
 * @author kurstan
 * @created at 26.03.2023 10:32
 */
@Component
public class MenuItemToMenuItemResponseConverter implements Converter<MenuItem, MenuItemResponse> {
    @Override
    public MenuItemResponse convert(MenuItem menuItem) {
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getImage(),
                menuItem.getPrice(),
                menuItem.getDescription(),
                menuItem.isVegetarian()
        );
    }
}
