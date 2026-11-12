package backend.clinic.repositories;

import backend.clinic.entities.Center;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CenterRepository extends JpaRepository<Center, UUID> {
}
