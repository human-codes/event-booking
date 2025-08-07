package uz.tridev.eventbooking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RegistrationId implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "event_id")
    private UUID eventId;



}
