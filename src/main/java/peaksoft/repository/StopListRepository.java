package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import peaksoft.dto.stopList.response.StopListResponse;
import peaksoft.entities.StopList;

import java.util.List;

@Repository
public interface StopListRepository extends JpaRepository<StopList, Long> {

    List<StopListResponse> findAllByMenuItemId(Long menuItemId);

}