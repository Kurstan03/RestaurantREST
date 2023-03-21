package peaksoft.dto.user.response;

import peaksoft.entities.enums.Role;

import java.time.LocalDate;

/**
 * @author kurstan
 * @created at 17.03.2023 17:11
 */
public record UserResponse(
        Long id,
        String fullName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        Role role,
        int experience
) {
}
