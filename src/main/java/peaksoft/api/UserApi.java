package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.user.request.AcceptOrRejectRequest;
import peaksoft.dto.user.request.RegisterRequest;
import peaksoft.dto.user.request.UserRequest;

import peaksoft.dto.user.response.*;
import peaksoft.service.UserService;

import java.util.List;

/**
 * @author kurstan
 * @created at 16.03.2023 20:10
 */
@RestController
@RequestMapping("/api/users")
public class UserApi {
    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<UserResponse> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    UserResponse findById(@PathVariable Long userId){
        return userService.findById(userId);
    }
    @PostMapping("/register")
    SimpleResponse signIn(@RequestBody @Valid RegisterRequest registerRequest){
        return userService.register(registerRequest);
    }

    @PostMapping("/authenticate")
    UserTokenResponse authenticate(@RequestBody UserRequest userRequest){
        return userService.authenticate(userRequest);
    }

    @GetMapping("/applications")
    @PreAuthorize("hasAuthority('ADMIN')")
    List<UserResponse> applications(){
        return userService.getApplications();
    }
    @PostMapping("/accept/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse acceptResponse(@PathVariable Long restaurantId,
                                  @RequestBody AcceptOrRejectRequest acceptOrRejectRequest){
        return userService.acceptResponse(restaurantId, acceptOrRejectRequest);
    }
    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    SimpleResponse updateUser(@PathVariable Long userId,
                              @RequestBody RegisterRequest request){
        return userService.updateUser(userId, request);
    }
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse deleteUser(@PathVariable Long userId){
        return userService.deleteUser(userId);
    }
}
