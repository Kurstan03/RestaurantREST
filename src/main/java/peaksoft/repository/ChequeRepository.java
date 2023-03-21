package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.cheque.response.ChequeResponse;
import peaksoft.dto.cheque.response.MenuItemForCheque;
import peaksoft.dto.cheque.response.SumAllChequeOfDay;
import peaksoft.entities.Cheque;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("select new peaksoft.dto.cheque.response.ChequeResponse(c.id, c.createdAt," +
            " concat(c.user.firstName,' ',c.user.lastName), sum(m.price), m.restaurant.service)" +
            " from Cheque c join c.menuItems m where c.user.id = ?1 group by c.id, c.createdAt," +
            " c.user.firstName, c.user.lastName, m.restaurant.service")
    List<ChequeResponse> getAllChequeByUserId (Long userId);

    @Query("select new peaksoft.dto.cheque.response.MenuItemForCheque(m.id, m.name, m.price, " +
            "count(m)) from MenuItem m join m.cheques c where c.id = ?1 group by m.id, m.name, m.price")
    List<MenuItemForCheque> getMeals(Long chequeId);

}