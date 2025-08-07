package uz.tridev.eventbooking.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationResponseDto(
        UUID eventId,
        String eventTitle,
        LocalDateTime datetime
) {}
