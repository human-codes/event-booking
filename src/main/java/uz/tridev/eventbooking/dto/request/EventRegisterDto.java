package uz.tridev.eventbooking.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EventRegisterDto(
    @NotNull(message = "Event ID is required")
    UUID eventId
) {}
