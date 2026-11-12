package backend.clinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "centers")
@AllArgsConstructor
@NoArgsConstructor
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "center_name")
    private String name;

    @Column(name = "center_city_location")
    private String city;

    @Column(name = "physical_address")
    private String address;

    @Column(name = "phone_number")
    private String phone;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}

// exclude opening_hours because we don't set it NOT NULL on database and also no need it on logic right now
