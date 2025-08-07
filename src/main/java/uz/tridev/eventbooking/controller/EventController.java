package uz.tridev.eventbooking.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.tridev.eventbooking.dto.request.EventRegisterDto;
import uz.tridev.eventbooking.dto.response.BaseResponse;
import uz.tridev.eventbooking.dto.request.EventCreateDto;
import uz.tridev.eventbooking.entity.Event;
import uz.tridev.eventbooking.service.impl.EventServiceI;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/event")
public class EventController {
    private final EventServiceI eventServiceI;

    public EventController(EventServiceI eventServiceI) {
        this.eventServiceI = eventServiceI;
    }


    @GetMapping
    public ResponseEntity<BaseResponse<Page<Event>>> getAllEvents(Pageable pageable) {
        return eventServiceI.getAllEvents(pageable);
    }

    @GetMapping("/my")
    public ResponseEntity<BaseResponse<Page<Event>>> getMyEvents(Pageable pageable) {
        return eventServiceI.getMyEvents(pageable);
    }

    @GetMapping("/registrations")
    public ResponseEntity<?> getMyRegistrations(Pageable pageable) {
        return eventServiceI.getMyRegistrations(pageable);
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createEvent(@RequestBody @Valid EventCreateDto eventCreateDto) {
        return eventServiceI.createEvent(eventCreateDto);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerForEvent(@Valid @RequestBody EventRegisterDto dto) {
        return eventServiceI.registerForEvent(dto.eventId());
    }

}
