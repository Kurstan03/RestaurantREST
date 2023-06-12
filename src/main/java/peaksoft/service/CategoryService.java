package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.category.request.CategoryRequest;
import peaksoft.dto.category.response.CategoriesResponse;
import peaksoft.dto.category.response.CategoryResponse;
import peaksoft.dto.pagination.PaginationResponse;

import java.util.List;

/**
 * @author kurstan
 * @created at 18.03.2023 2:29
 */
public interface CategoryService {
    List<CategoriesResponse> getAll();

    SimpleResponse save(CategoryRequest categoryRequest);

    SimpleResponse update(Long categoryId, CategoryRequest categoryRequest);

    SimpleResponse delete(Long categoryId);

    CategoryResponse categorySubCategories(Long categoryId);

    PaginationResponse<CategoriesResponse> getCategoryPagination(int page, int size);

}
