package backend.clinic.dtos.slots;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record SlotRequest(@NotNull LocalDate date,@NotNull LocalTime startsAt, @NotNull LocalTime endsAt,
                          @NotNull @Min(value = 1) @Max(value = 50)  int capacityPerHour) {
}
