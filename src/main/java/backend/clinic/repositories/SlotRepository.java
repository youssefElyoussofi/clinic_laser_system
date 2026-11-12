package backend.clinic.repositories;

import backend.clinic.entities.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.UUID;

public interface SlotRepository extends JpaRepository<Slot, UUID> {
    boolean existsByStartAt(Instant startAt);
}
