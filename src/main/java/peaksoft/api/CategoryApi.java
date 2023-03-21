package peaksoft.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.category.response.CategoryResponse;
import peaksoft.dto.category.request.CategoryRequest;
import peaksoft.service.CategoryService;

import java.util.List;

/**
 * @author kurstan
 * @created at 18.03.2023 2:27
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryApi {
    private final CategoryService categoryService;

    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<CategoryResponse> getAll(){
        return categoryService.getAll();
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse save(@RequestBody CategoryRequest categoryRequest){
        return categoryService.save(categoryRequest);
    }
    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse update(@PathVariable Long categoryId,
                          @RequestBody CategoryRequest categoryRequest){
        return categoryService.update(categoryId, categoryRequest);
    }
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse delete(@PathVariable Long categoryId){
        return categoryService.delete(categoryId);
    }
}
