package backend.clinic.controllers;

import backend.clinic.dtos.slots.SlotRequest;
import backend.clinic.dtos.slots.SlotResponse;
import backend.clinic.services.SlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@RestController
public class SlotController {
    private final SlotService slotService;

    @PostMapping("/centers/{centerId}/slots")
    public ResponseEntity<List<SlotResponse>> createSlot(@PathVariable UUID centerId , @Valid @RequestBody SlotRequest request){
        // hardcoded user id of staff we'll add it later when use logic auth
        final UUID staffUserId = UUID.fromString("3f6f930e-7507-40d4-8a30-81e0f679f61e");

        return ResponseEntity.ok(slotService.createSlot(staffUserId,centerId,request));
    }


}
