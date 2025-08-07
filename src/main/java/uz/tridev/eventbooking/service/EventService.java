package uz.tridev.eventbooking.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.tridev.eventbooking.dto.response.BaseResponse;
import uz.tridev.eventbooking.dto.request.EventCreateDto;
import uz.tridev.eventbooking.entity.Event;
import uz.tridev.eventbooking.entity.Registration;
import uz.tridev.eventbooking.entity.RegistrationId;
import uz.tridev.eventbooking.entity.User;
import uz.tridev.eventbooking.repository.EventRepository;
import uz.tridev.eventbooking.repository.RegistrationRepository;
import uz.tridev.eventbooking.repository.UserRepository;
import uz.tridev.eventbooking.service.impl.EventServiceI;

import java.util.List;
import java.util.UUID;

@Service
public class EventService implements EventServiceI {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository, RegistrationRepository registrationRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public ResponseEntity<BaseResponse<Page<Event>>> getAllEvents(Pageable pageable) {
        Page<Event> page = eventRepository.findAll(pageable);

        String message = page.isEmpty() ? "No events available at the moment" : "Fetched successfully";
        return ResponseEntity.ok(new BaseResponse<>(true, message, page));
    }

    @Override
    public ResponseEntity<BaseResponse<Page<Event>>> getMyEvents(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Page<Event> events = eventRepository.findByOrganizer(user,pageable);

        if (events.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(true, "No events available", events));
        }

        return ResponseEntity.ok(new BaseResponse<>(true, "Fetched successfully", events));
    }

    @Override
    public ResponseEntity<BaseResponse> createEvent(EventCreateDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Event event = new Event();
        event.setTitle(dto.title());
        event.setDescription(dto.description());
        event.setLocation(dto.location());
        event.setDatetime(dto.datetime());
        event.setOrganizer(user);
        eventRepository.save(event);

        return ResponseEntity.ok(new BaseResponse<>(true, "New event created successfully", event));
    }

    @Override
    public ResponseEntity<?> registerForEvent(UUID eventId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        RegistrationId registrationId = new RegistrationId(user.getId(), event.getId());

        if (registrationRepository.existsById(registrationId)) {
            throw new IllegalStateException("User already registered for this event");
        }

        Registration registration = new Registration();
        registration.setId(registrationId);
        registration.setUser(user);
        registration.setEvent(event);
        registrationRepository.save(registration);

        return ResponseEntity.ok(new BaseResponse<>(true, "Participation registered successfully", registration));
    }

    @Override
    public ResponseEntity<BaseResponse<Page<Registration>>> getMyRegistrations(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Page<Registration> registrations = registrationRepository.findAllByUser(user,pageable);

        if (registrations.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(true, "No registrations found", registrations));
        }

        return ResponseEntity.ok(
                new BaseResponse<>(
                        true,
                        "User's registrations retrieved successfully",
                        registrations
                )
        );
    }


}
