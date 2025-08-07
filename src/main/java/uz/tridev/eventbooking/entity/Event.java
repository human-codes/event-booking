package uz.tridev.eventbooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event extends BaseEntity {

    private String title;
    private String description;
    private String location;
    private LocalDateTime datetime;
    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;


}
