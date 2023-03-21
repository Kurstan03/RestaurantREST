package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.user.request.AcceptOrRejectRequest;
import peaksoft.dto.user.request.RegisterRequest;
import peaksoft.dto.user.request.UserRequest;
import peaksoft.dto.user.response.*;
import peaksoft.entities.User;
import peaksoft.entities.enums.Role;
import peaksoft.exception.ExistsException;
import peaksoft.exception.NoVacancyException;
import peaksoft.exception.NoValidException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.security.jwt.JwtUtil;
import peaksoft.service.UserService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author kurstan
 * @created at 16.03.2023 20:20
 */
@Service
@Builder
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RestaurantRepository restaurantRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.restaurantRepository = restaurantRepository;
    }

    @PostConstruct
    public void init() {
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("admin22"));
            user.setRole(Role.ADMIN);
            user.setAccepted(true);
            userRepository.save(user);
        }
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.getAllUsers();
    }

    @Override
    public SimpleResponse register(RegisterRequest registerRequest) {
        if (userRepository.getAllUsers().size() == 15) {
            log.error("There are currently no open vacancies");
            throw new NoVacancyException("There are currently no open vacancies");
        }
        if (userRepository.existsByEmail(registerRequest.email())) {
            log.error(String.format("User with login: %s is exists", registerRequest.email()));
            throw new ExistsException(String.format(
                    "User with login: %s is exists", registerRequest.email()
            ));
        }
        checkForValid(registerRequest);
        User user = User.builder()
                .firstName(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .dateOfBirth(registerRequest.dateOfBirth())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .phoneNumber(registerRequest.phoneNumber())
                .role(registerRequest.role())
                .experience(registerRequest.experience())
                .accepted(false)
                .build();
        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Your application successfully sent!")
                .build();
    }

    @Override
    public UserTokenResponse authenticate(UserRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRequest.login(),
                userRequest.password()
        ));

        User user = userRepository.findByEmail(userRequest.login()).orElseThrow(() -> {
            log.error(String.format("User %s is not found!", userRequest.login()));
            throw new NotFoundException(String.format("User %s is not found!", userRequest.login()));
        });
        if (user.isAccepted()) {
            return UserTokenResponse.builder()
                    .login(userRequest.login())
                    .token(jwtUtil.generateToken(user))
                    .build();
        }
        return UserTokenResponse.builder()
                .login(userRequest.login())
                .build();
    }
    @Override
    public List<UserResponse> getApplications() {
        return userRepository.getAllApplication();
    }

    @Override
    public SimpleResponse acceptResponse(Long restaurantId, AcceptOrRejectRequest acceptOrRejectRequest) {
        User user = userRepository.findById(acceptOrRejectRequest.userId())
                .orElseThrow(() ->{
                    log.error(String.format("User with id - %s is not found!",
                            acceptOrRejectRequest.userId()));
                    throw new NotFoundException(String.format("User with id - %s is not found!",
                            acceptOrRejectRequest.userId()));
                });
        if (acceptOrRejectRequest.accept()){
            user.setAccepted(true);
            user.setRestaurant(restaurantRepository.findById(restaurantId).orElseThrow(()->{
                log.error("Restaurant not found!");
                throw new NotFoundException("Restaurant not found!");
            }));
            log.info(String.format("User - %s is accepted!", user.getEmail()));
            return new SimpleResponse(
                    HttpStatus.ACCEPTED,
                   String.format("User - %s is accepted!", user.getEmail())
            );
        } else {
            log.info(String.format("User - %s is rejected!", user.getEmail()));
            userRepository.delete(user);
            return new SimpleResponse(
                    HttpStatus.NOT_ACCEPTABLE,
                    String.format("User - %s is rejected!", user.getEmail())
            );
        }
    }

    @Override
    public SimpleResponse updateUser(Long userId, RegisterRequest registerRequest) {
        for (User user : userRepository.findAll()) {
            if (!user.getId().equals(userId) && user.getEmail().equals(registerRequest.email())) {
                log.error(String.format("User with login: %s is exists", registerRequest.email()));
                throw new ExistsException(String.format(
                        "User with login: %s is exists", registerRequest.email()
                ));
            }
        }
        checkForValid(registerRequest);
        User oldUser = userRepository.findById(userId).orElseThrow(() -> {
            log.error(String.format("User with id - %s is not found!", userId));
            throw new NotFoundException(String.format("User with id - %s is not found!", userId));
        });
        oldUser.setFirstName(registerRequest.firstName());
        oldUser.setLastName(registerRequest.lastName());
        oldUser.setDateOfBirth(registerRequest.dateOfBirth());
        oldUser.setEmail(registerRequest.email());
        oldUser.setPhoneNumber(registerRequest.phoneNumber());
        oldUser.setRole(registerRequest.role());
        oldUser.setExperience(registerRequest.experience());

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description(String.format("User with id - %s is updated!",userId))
                .build();
    }

    private void checkForValid(RegisterRequest registerRequest) {

        int age = LocalDate.now().minusYears(registerRequest.dateOfBirth().getYear()).getYear();
        if (registerRequest.role() == Role.CHEF) {
            if (age < 25 || age > 45) {
                log.error("Chef must be between 25 and 45 years of age");
                throw new NoValidException("Chef must be between 25 and 45 years of age");
            }
            if (registerRequest.experience() < 2) {
                log.error("Chef experience must be more than 2 years");
                throw new NoValidException("Chef experience must be more than 2 years");
            }
        } else if (registerRequest.role() == Role.WAITER) {
            if (age < 18 || age > 30) {
                log.error("Waiter must be between 18 and 30 years of age");
                throw new NoValidException("Waiter must be between 18 and 30 years of age");
            }
            if (registerRequest.experience() < 1) {
                log.error("Waiter experience must be more than 1 year");
                throw new NoValidException("Waiter experience must be more than 1 year");
            }
        }
    }

    @Override
    public SimpleResponse deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.info(String.format("User with id - %s is not found!", userId));
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .description(String.format("User with id - %s is not found!", userId))
                    .build();
        }
        userRepository.deleteById(userId);
        log.info(String.format("User with id - %s is deleted!", userId));
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("User with id - %s is deleted!", userId)
        );
    }
}
