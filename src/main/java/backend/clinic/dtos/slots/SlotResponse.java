package backend.clinic.dtos.slots;


import backend.clinic.entities.Slot;

import java.time.Instant;
import java.util.UUID;

public record SlotResponse(UUID slotId, Instant startsAt, Instant endsAt, int capacity, int bookedCount,
                           UUID centerId,String centerName,int remainingSeats,boolean isFull) {
    public static SlotResponse from(Slot slot){
        return new SlotResponse(slot.getId(),slot.getStartAt(),slot.getEndsAt(),slot.getCapacity(),slot.getBookCount(),
                slot.getCenter().getId(),slot.getCenter().getName(),slot.remainingSeats(),slot.isFull());
    }
}
