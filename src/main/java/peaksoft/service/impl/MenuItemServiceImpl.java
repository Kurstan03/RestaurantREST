package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.menuItem.request.MenuItemRequest;
import peaksoft.dto.menuItem.response.MenuItemResponse;
import peaksoft.entities.MenuItem;
import peaksoft.entities.Restaurant;
import peaksoft.entities.Subcategory;
import peaksoft.exception.ExistsException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kurstan
 * @created at 18.03.2023 15:21
 */
@Service
@Transactional
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubCategoryRepository subCategoryRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository,
                               RestaurantRepository restaurantRepository,
                               SubCategoryRepository subCategoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public List<MenuItemResponse> getAll(Long subCategoryId, String keyWord) {
        List<MenuItemResponse> list = new ArrayList<>();
        if (keyWord == null) {
            list.addAll(menuItemRepository.getAll());
            list.addAll(menuItemRepository.getMenuItemByStopListsNull());
            return list;
        }
        list.addAll(menuItemRepository.globalSearch(keyWord));
//        list.addAll(menuItemRepository.globalSearchStopListsNull(keyWord));
        return list;
    }

    @Override
    public SimpleResponse save(Long restaurantId, Long subCategoryId, MenuItemRequest menuItemRequest) {
        if (menuItemRequest.price().intValue() < 0) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .description("Price can't be negative number!")
                    .build();
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());

        Subcategory subcategory = subCategoryRepository.findById(subCategoryId).orElseThrow(
                () -> new NotFoundException("Sub category not found!"));
        menuItem.setSubcategory(subcategory);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new NotFoundException("Restaurant not found!"));
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Menu item - " + menuItem.getName() + " is saved!")
                .build();
    }

    @Override
    public SimpleResponse update(Long menuItemId, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(
                () -> new NotFoundException("Menu with id - " + menuItemId + " is not found!"));
        if (menuItemRequest.price().intValue() < 0) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .description("Price can't be negative number!")
                    .build();
        }
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Menu item - " + menuItem.getName() + " is updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long menuItemId) {
        if (!menuItemRepository.existsById(menuItemId)) {
            throw new ExistsException("Menu item with id - " + menuItemId + " doesn't exists!");
        }
        menuItemRepository.deleteById(menuItemId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Menu item with id - " + menuItemId + " is deleted!")
                .build();
    }

    @Override
    public List<MenuItemResponse> sort(String ascOrDesc) {
        if (ascOrDesc.equals("desc")){
           return menuItemRepository.getAllByOrderByPriceDesc();
        } else {
            return menuItemRepository.getAllByOrderByPriceAsc();
        }
    }

    @Override
    public List<MenuItemResponse> isVegetarian(boolean isTrue) {
        return menuItemRepository.findMenuItemByIsVegetarian(isTrue);
    }
}
