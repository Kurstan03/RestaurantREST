package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.stopList.request.StopListRequest;
import peaksoft.dto.stopList.response.StopListResponse;
import peaksoft.entities.MenuItem;
import peaksoft.entities.StopList;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;

import java.util.List;

/**
 * @author kurstan
 * @created at 20.03.2023 10:31
 */
@Slf4j
@Service
@Transactional
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    public StopListServiceImpl(StopListRepository stopListRepository,
                               MenuItemRepository menuItemRepository) {
        this.stopListRepository = stopListRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<StopListResponse> getStopLists(Long menuItemId) {
        return stopListRepository.findAllByMenuItemId(menuItemId);
    }

    @Override
    public SimpleResponse create(Long menuItemId, StopListRequest stopListRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> {
            log.error("Menu item with id - " + menuItemId + " is not found!");
            throw new NotFoundException("Menu item with id - " + menuItemId + " is not found!");
        });
        for (StopList stopList : menuItem.getStopLists()) {
            if (stopList.getDate().equals(stopListRequest.date())){
                log.info("Stop list for this day already exists!");
                return SimpleResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .description("Stop list for this day already exists!")
                        .build();
            }
        }

        StopList stopList = new StopList();
        stopList.setReason(stopListRequest.reason());
        stopList.setDate(stopListRequest.date());
        stopList.setMenuItem(menuItem);
        stopListRepository.save(stopList);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Stop list created!")
                .build();
    }

    @Override
    public SimpleResponse update(Long menuItemId, Long stopListId, StopListRequest stopListRequest) {
        MenuItem menuItem;
        menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> {
            log.error("Menu item with id - " + menuItemId);
            throw new NotFoundException("Menu item with id - " + menuItemId);
        });

        for (StopList stopList : menuItem.getStopLists()) {
            if (stopList.getDate().equals(stopListRequest.date())){
                log.info("Stop list for this day already exists!");
                return SimpleResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .description("Stop list for this day already exists!")
                        .build();
            }
        }
        StopList stopList = stopListRepository.findById(stopListId).orElseThrow(() -> {
            log.error("Stop list with id - " + stopListId + " is not found!");
            throw new NotFoundException("Stop list with id - " + stopListId + " is not found!");
        });
        stopList.setReason(stopListRequest.reason());
        stopList.setDate(stopListRequest.date());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Stop list updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long menuItemId, Long stopListId) {
        if (!stopListRepository.existsById(stopListId)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .description("Stop list with id - " + stopListId + " doesn't exists!")
                    .build();
        }
        StopList stopList = stopListRepository.findById(stopListId).orElseThrow(() ->
                new NotFoundException("Stop list with id - " + stopListId + " doesn't exists!"));
        stopListRepository.delete(stopList);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Stop list with id - " + stopListId + " is deleted!")
                .build();
    }
}
