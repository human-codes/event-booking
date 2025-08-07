package uz.tridev.eventbooking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.tridev.eventbooking.entity.Registration;
import uz.tridev.eventbooking.entity.RegistrationId;
import uz.tridev.eventbooking.entity.User;


public interface RegistrationRepository extends JpaRepository<Registration, RegistrationId> {
    Page<Registration> findAllByUser(User user, Pageable pageable);
}