package uz.tridev.eventbooking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.tridev.eventbooking.entity.Event;
import uz.tridev.eventbooking.entity.User;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    Page<Event> findAll(Pageable pageable);

    Page<Event> findByOrganizer(User organizer, Pageable pageable);
}