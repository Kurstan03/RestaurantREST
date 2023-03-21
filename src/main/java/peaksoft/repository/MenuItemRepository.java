package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.menuItem.response.MenuItemResponse;
import peaksoft.entities.MenuItem;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("select distinct new peaksoft.dto.menuItem.response.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) " +
            "from MenuItem m join StopList s on s.menuItem.id = m.id where s.date != current date ")
    List<MenuItemResponse> getAll();

    List<MenuItemResponse> getMenuItemByStopListsNull();

    @Query("select new peaksoft.dto.menuItem.response.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) " +
            "from MenuItem m where m.name ilike concat('%', :keyWord, '%') or m.description " +
            "ilike concat('%', :keyWord, '%') or m.subcategory.name ilike concat('%', :keyWord, '%')" +
            " or m.subcategory.category.name ilike concat('%', :keyWord, '%')")
    List<MenuItemResponse> globalSearch(String keyWord);

    @Query("select new peaksoft.dto.menuItem.response.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) " +
            "from MenuItem m where m.stopLists = null and (m.name ilike concat('%', :keyWord, '%')" +
            " or m.subcategory.name ilike concat('%', :keyWord, '%') or m.subcategory.category.name " +
            "ilike concat('%', :keyWord, '%'))")
    List<MenuItemResponse> globalSearchStopListsNull(String keyWord);

    List<MenuItemResponse> getAllByOrderByPriceDesc();

    List<MenuItemResponse> getAllByOrderByPriceAsc();

    List<MenuItemResponse> findMenuItemByIsVegetarian(boolean isTrue);
}