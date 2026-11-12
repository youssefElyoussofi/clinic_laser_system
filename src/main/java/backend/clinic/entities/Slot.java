package backend.clinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "slots")
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    @Column(name = "starts_at")
    private Instant startAt;

    @Column(name = "ends_at")
    private Instant endsAt;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "booked_count")
    private int bookCount = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    public boolean isFull(){
        return bookCount >= capacity;
    }

    public int remainingSeats(){
        return Math.max(capacity - bookCount,0);
    }

    public void incrementBookCount(){
        this.bookCount++;
    }
}
