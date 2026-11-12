package backend.clinic.services;

import backend.clinic.entities.User;
import backend.clinic.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User getEntity(UUID userId){
        return  userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
    }
}
