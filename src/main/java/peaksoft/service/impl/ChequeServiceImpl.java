package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.cheque.request.ChequeRequest;
import peaksoft.dto.cheque.response.ChequeResponse;
import peaksoft.dto.cheque.response.MenuItemForCheque;
import peaksoft.dto.cheque.response.SumAllChequeOfDay;
import peaksoft.entities.Cheque;
import peaksoft.entities.MenuItem;
import peaksoft.entities.User;
import peaksoft.exception.ExistsException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kurstan
 * @created at 19.03.2023 14:32
 */
@Service
@Transactional
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;

    public ChequeServiceImpl(ChequeRepository chequeRepository,
                             MenuItemRepository menuItemRepository,
                             UserRepository userRepository) {
        this.chequeRepository = chequeRepository;
        this.menuItemRepository = menuItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ChequeResponse> getAll(Long userId) {
        List<ChequeResponse> cheques = new ArrayList<>();
        for (ChequeResponse c : chequeRepository.getAllChequeByUserId(userId)) {
            BigDecimal total = c.getAveragePrice().multiply(new BigDecimal(c.getService())).divide(new BigDecimal(100)).add(c.getAveragePrice());
            c.setGrandTotal(total);
            c.setMeals(chequeRepository.getMeals(c.getId()));
            cheques.add(c);
        }
        return cheques;
    }

    @Override
    public SimpleResponse createCheque(Long userId, ChequeRequest chequeRequest) {
        List<MenuItem> menuItems = new ArrayList<>();
        Cheque cheque = new Cheque();
        cheque.setCreatedAt(LocalDate.now());
        for (Long mealId : chequeRequest.mealsId()) {
            MenuItem menuItem = menuItemRepository.findById(mealId).orElseThrow(
                    ()-> new NotFoundException("Meal with id - " + mealId + " is not found!"));
            menuItems.add(menuItem);
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id - " + userId + " not found!"));
        cheque.setUser(user);

        for (MenuItem menuItem : menuItems) {
            menuItem.getCheques().add(cheque);
        }
        chequeRepository.save(cheque);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Your check has been accepted!")
                .build();
    }

    @Override
    public SimpleResponse update(Long chequeId, ChequeRequest chequeRequest) {
        List<MenuItem> menuItems;
        Cheque cheque = chequeRepository.findById(chequeId).orElseThrow(
                () -> new NotFoundException("Cheque with id - " + chequeId + " is not found!"));
        menuItems = chequeRequest.mealsId().stream().map(mealId -> menuItemRepository.findById(mealId).orElseThrow(
                () -> new NotFoundException("Meal with id - " + mealId + " is not found!"))).collect(Collectors.toList());

        for (MenuItem menuItem : cheque.getMenuItems()) {
            menuItem.getCheques().remove(cheque);
        }
        for (MenuItem menuItem : menuItems) {
            menuItem.getCheques().add(cheque);
        }
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Your check has been updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long chequeId) {
        if (!chequeRepository.existsById(chequeId)) {
            throw new ExistsException("Cheque with id - " + chequeId +" doesn't exists!");
        }
        Cheque cheque = chequeRepository.findById(chequeId).orElseThrow(()->
                new NotFoundException("Cheque with id - " + chequeId +" doesn't exists!"));
        cheque.getMenuItems().forEach(menuItem -> menuItem.getCheques().remove(cheque));
        chequeRepository.deleteById(chequeId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Cheque with id - " + chequeId + " is deleted!")
                .build();
    }

    @Override
    public SumAllChequeOfDay sumAllChequeOfDay(Long userId, LocalDate date) {
        List<Cheque> cheques = chequeRepository.findAll();
        SumAllChequeOfDay sumAllChequeOfDay = new SumAllChequeOfDay();
        Long count = 0L;
        BigDecimal totalSum = new BigDecimal(0);
        int ser = 1;
        for (Cheque cheque : cheques) {
            if (cheque.getUser().getId().equals(userId) && cheque.getCreatedAt().equals(date)){
                sumAllChequeOfDay.setWaiter(cheque.getUser().getFirstName()+" "+cheque.getUser().getLastName());
                sumAllChequeOfDay.setDate(cheque.getCreatedAt());
                count++;
                for (MenuItem menuItem : cheque.getMenuItems()) {
                    totalSum = new BigDecimal(totalSum.intValue() + menuItem.getPrice().intValue());
                    ser = menuItem.getRestaurant().getService();
                }
            }

        }
        BigDecimal service = totalSum.multiply(new BigDecimal(ser)).divide(new BigDecimal(100));
        sumAllChequeOfDay.setCounterOfCheque(count);
        sumAllChequeOfDay.setTotalSumma(totalSum.add(service));
        return sumAllChequeOfDay;
    }

    @Override
    public SimpleResponse avgCheque(Long userId, LocalDate date) {
        int total = 0;
        int count = 0;
        int ser = 1;
        for (Cheque cheque : chequeRepository.findAll()) {
            if (cheque.getCreatedAt().equals(date)) {
                for (MenuItem menuItem : cheque.getMenuItems()) {
                    total += menuItem.getPrice().intValue();
                    ser = menuItem.getRestaurant().getService();
                }
                count++;
            }
        }
        total += total * ser / 100;
        int avg = total / count;
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Average restaurant cheque - " + avg)
                .build();
    }
}
