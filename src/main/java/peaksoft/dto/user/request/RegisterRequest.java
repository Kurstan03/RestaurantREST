package peaksoft.dto.user.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import peaksoft.entities.enums.Role;

import java.time.LocalDate;

/**
 * @author kurstan
 * @created at 16.03.2023 20:50
 */
public record RegisterRequest(
        @NotNull(message = "first name should not be null")
        String firstName,
        @NotNull(message = "last name should not be null")
        String lastName,
        @NotNull(message = "date of birth should not be null")
        LocalDate dateOfBirth,
        @NotNull(message = "email should not be null")
        String email,
        @NotNull(message = "password should not be null")
        @Size(min = 4, message = "password should be more than 4 characters")
        String password,
        @NotNull(message = "phone number should not be null")
        @Pattern(regexp = "\\+996\\d{9}", message = "Phone number should start with +996 and consist of 13 characters!")
        String phoneNumber,
        @NotNull(message = "role should not be null")
        Role role,
        @NotNull(message = "experience should not be null")
        int experience
) {
}
