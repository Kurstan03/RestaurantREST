package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.category.response.CategoryResponse;
import peaksoft.entities.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select new peaksoft.dto.category.response.CategoryResponse(c.id, c.name) from Category c")
    List<CategoryResponse> getAll();
}