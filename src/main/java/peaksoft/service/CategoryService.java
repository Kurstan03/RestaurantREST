package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.category.request.CategoryRequest;
import peaksoft.dto.category.response.CategoryResponse;

import java.util.List;

/**
 * @author kurstan
 * @created at 18.03.2023 2:29
 */
public interface CategoryService {
    List<CategoryResponse> getAll();

    SimpleResponse save(CategoryRequest categoryRequest);

    SimpleResponse update(Long categoryId, CategoryRequest categoryRequest);

    SimpleResponse delete(Long categoryId);

}
