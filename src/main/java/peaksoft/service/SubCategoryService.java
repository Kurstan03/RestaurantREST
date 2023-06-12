package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.subCategory.request.SubCategoryRequest;
import peaksoft.dto.subCategory.response.SubCategoryResponse;

import java.util.List;
import java.util.Map;

/**
 * @author kurstan
 * @created at 18.03.2023 10:59
 */
public interface SubCategoryService {
    List<SubCategoryResponse> getAllByCategoryId(Long categoryId);

    SimpleResponse save(Long categoryId, SubCategoryRequest subCategoryRequest);

    SimpleResponse update(Long subCategoryId, SubCategoryRequest subCategoryRequest);

    SimpleResponse delete(Long subCategoryId);

    Map<String, SubCategoryResponse> groupByCategory(String categoryId);

    SubCategoryResponse findById(Long subCategoryId);
}
