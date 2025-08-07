package uz.tridev.eventbooking.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventCreateDto(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Description is required")
        String description,

        @NotBlank(message = "Location is required")
        String location,

        @NotNull(message = "Event date and time is required")
        @Future(message = "Event date and time must be in the future")
        LocalDateTime datetime
) {
}
