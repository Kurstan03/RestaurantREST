package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.subCategory.response.SubCategoryResponse;
import peaksoft.entities.Category;
import peaksoft.entities.Subcategory;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<Subcategory, Long> {

    @Query("select new peaksoft.dto.subCategory.response.SubCategoryResponse(s.id, s.name)" +
            " from Subcategory s where s.category.id = ?1")
    List<SubCategoryResponse> getAllByCategoryId(Long categoryId);
}