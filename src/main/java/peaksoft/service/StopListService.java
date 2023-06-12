package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.stopList.request.StopListRequest;
import peaksoft.dto.stopList.response.StopListResponse;

import java.util.List;

/**
 * @author kurstan
 * @created at 20.03.2023 10:31
 */
public interface StopListService {
    List<StopListResponse> getStopLists(Long menuItemId);

    SimpleResponse create(Long menuItemId, StopListRequest stopListRequest);

    SimpleResponse update(Long menuItemId, Long stopListId, StopListRequest stopListRequest);

    SimpleResponse delete(Long menuItemId, Long stopListId);

    StopListResponse findById(Long stopListId);

}
