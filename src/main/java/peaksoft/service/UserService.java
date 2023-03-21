package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.user.request.AcceptOrRejectRequest;
import peaksoft.dto.user.request.RegisterRequest;
import peaksoft.dto.user.request.UserRequest;
import peaksoft.dto.user.response.*;

import java.util.List;

/**
 * @author kurstan
 * @created at 16.03.2023 20:11
 */
public interface UserService {
    List<UserResponse> getAll();

    SimpleResponse register(RegisterRequest registerRequest);

    UserTokenResponse authenticate(UserRequest userRequest);

    List<UserResponse> getApplications();

    SimpleResponse acceptResponse(Long restaurantId, AcceptOrRejectRequest acceptOrRejectRequest);

    SimpleResponse updateUser(Long userId, RegisterRequest request);

    SimpleResponse deleteUser(Long userId);

}
