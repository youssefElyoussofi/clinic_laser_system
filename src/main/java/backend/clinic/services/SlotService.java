package backend.clinic.services;

import backend.clinic.dtos.slots.SlotRequest;
import backend.clinic.dtos.slots.SlotResponse;
import backend.clinic.entities.Center;
import backend.clinic.entities.Slot;
import backend.clinic.repositories.SlotRepository;
import backend.clinic.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SlotService {
    private final SlotRepository slotRepository;
    private final UserService userService;
    private final CenterService centerService;

    public static ZoneId zoneId = ZoneId.of("Africa/Casablanca");

    @Transactional
    public List<SlotResponse> createSlot(UUID staffId,UUID centerId, SlotRequest request){

        Center center = centerService.getEntity(centerId);

        LocalTime strictStartTime = request.startsAt().withMinute(0).withSecond(0).withNano(0);
        LocalTime strictEndTime = request.endsAt().withMinute(0).withSecond(0).withNano(0);
        if (LocalDateTime.of(request.date(),strictStartTime).atZone(zoneId).toInstant().isBefore(Instant.now()))
            throw new RuntimeException("slot start time in the past");

        if (strictEndTime.isBefore(strictStartTime))
            throw new RuntimeException("slot end time cannot before start time");

        List<SlotResponse> slots = new ArrayList<>();
        for (LocalTime time = strictStartTime;strictEndTime.compareTo(time) > 0;time = time.plusHours(1)){
            Instant start = LocalDateTime.of(request.date(),time).atZone(zoneId).toInstant();
            Instant end = LocalDateTime.of(request.date(),time.plusHours(1)).atZone(zoneId).toInstant();

            if (slotRepository.existsByStartAt(start))
                continue;

            Slot slot = new Slot();
            slot.setStartAt(start);
            slot.setEndsAt(end);
            slot.setCapacity(request.capacityPerHour());
            slot.setCenter(center);

            Slot saved = slotRepository.save(slot);

            slots.add(SlotResponse.from(saved));
        }

        return slots;
    }
}
