package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.category.response.CategoriesResponse;
import peaksoft.dto.category.response.CategoryResponse;
import peaksoft.entities.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select new peaksoft.dto.category.response.CategoriesResponse(c.id, c.name) from Category c")
    List<CategoriesResponse> getAll();

    @Query("select new peaksoft.dto.category.response.CategoriesResponse(c.id, c.name) from Category c")
    Page<CategoriesResponse> pagination(Pageable pageable);
}