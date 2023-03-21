package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.cheque.request.ChequeRequest;
import peaksoft.dto.cheque.response.ChequeResponse;
import peaksoft.dto.cheque.response.SumAllChequeOfDay;

import java.time.LocalDate;
import java.util.List;

/**
 * @author kurstan
 * @created at 19.03.2023 14:31
 */
public interface ChequeService {
    List<ChequeResponse> getAll(Long userId);

    SimpleResponse createCheque(Long userId, ChequeRequest chequeRequest);

    SimpleResponse update(Long chequeId, ChequeRequest chequeRequest);

    SimpleResponse delete(Long chequeId);

    SumAllChequeOfDay sumAllChequeOfDay(Long userId, LocalDate date);

    SimpleResponse avgCheque(Long userId, LocalDate date);
}
