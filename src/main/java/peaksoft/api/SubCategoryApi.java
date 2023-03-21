package peaksoft.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.subCategory.request.SubCategoryRequest;
import peaksoft.dto.subCategory.response.SubCategoryResponse;
import peaksoft.service.SubCategoryService;

import java.util.List;

/**
 * @author kurstan
 * @created at 18.03.2023 10:56
 */
@RestController
@RequestMapping("/api/{categoryId}/subCategories")
public class SubCategoryApi {
    private final SubCategoryService subCategoryService;

    public SubCategoryApi(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<SubCategoryResponse> getAll(@PathVariable Long categoryId){
        return subCategoryService.getAllByCategoryId(categoryId);
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse save(@PathVariable Long categoryId,
                        @RequestBody SubCategoryRequest subCategoryRequest){
        return subCategoryService.save(categoryId, subCategoryRequest);
    }
    @PutMapping("/{subCategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse update(@PathVariable Long categoryId,
                          @PathVariable Long subCategoryId,
                          @RequestBody SubCategoryRequest subCategoryRequest){
        return subCategoryService.update(subCategoryId, subCategoryRequest);
    }
    @DeleteMapping("/{subCategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse delete(@PathVariable Long categoryId,
                          @PathVariable Long subCategoryId){
        return subCategoryService.delete(subCategoryId);
    }
}