package uz.tridev.eventbooking.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.tridev.eventbooking.dto.response.BaseResponse;
import uz.tridev.eventbooking.dto.request.EventCreateDto;
import uz.tridev.eventbooking.entity.Event;
import uz.tridev.eventbooking.entity.Registration;

import java.util.List;
import java.util.UUID;

public interface EventServiceI {
    ResponseEntity<BaseResponse<Page<Event>>> getAllEvents(Pageable pageable);
    ResponseEntity<BaseResponse<Page<Event>>> getMyEvents(Pageable pageable);
    ResponseEntity<BaseResponse> createEvent(EventCreateDto eventCreateDto);
    ResponseEntity<?> registerForEvent(UUID eventId);
    ResponseEntity<BaseResponse<Page<Registration>>> getMyRegistrations(Pageable pageable);
}
