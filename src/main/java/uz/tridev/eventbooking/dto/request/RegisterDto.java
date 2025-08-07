package uz.tridev.eventbooking.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDto(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 20)
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid and contain @")
        String email,

        @NotBlank(message = "Password is required")
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[\\W_]).{6,}$",
                message = "Password must be at least 6 characters and include at least one number and one symbol"
        )
        String password

) {
}
