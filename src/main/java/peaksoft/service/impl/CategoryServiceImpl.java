package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.category.request.CategoryRequest;
import peaksoft.dto.category.response.CategoryResponse;
import peaksoft.entities.Category;
import peaksoft.entities.Restaurant;
import peaksoft.exception.ExistsException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.service.CategoryService;

import java.util.List;

/**
 * @author kurstan
 * @created at 18.03.2023 2:29
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               RestaurantRepository restaurantRepository) {
        this.categoryRepository = categoryRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.getAll();
    }

    @Override
    public SimpleResponse save(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Category - " + category.getName() + " is saved!")
                .build();
    }

    @Override
    public SimpleResponse update(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " is not found!"));
        category.setName(categoryRequest.name());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Category - " + category.getName() + " is updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ExistsException("Category with id - " + categoryId + " doesn't exists!");
        }
        categoryRepository.deleteById(categoryId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Category with id - " + categoryId + " is deleted")
                .build();
    }

}
