package backend.clinic.services;

import backend.clinic.entities.Center;
import backend.clinic.repositories.CenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CenterService {
    private final CenterRepository centerRepository;

    @Transactional
    public Center getEntity(UUID centerId){
        return centerRepository.findById(centerId).orElseThrow(()-> new RuntimeException("center not found"));
    }
}
