package uz.hrmanager.hrmanager.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uz.hrmanager.hrmanager.enums.LeaveType;
import java.time.LocalDate;

@Getter
@Setter
public class LeaveRequestCreateDTO {

    @NotNull(message = "Ariza mazmuni bo'sh bo'lmasligi kerak")
    private String requestContent;
    @NotNull(message = "Ta'til turi bo'sh bo'lmasligi kerak")
    private LeaveType leaveType;

    @NotNull(message = "Boshlanish sanasi bo'sh bo'lmasligi kerak")
    @FutureOrPresent(message = "Boshlanish sanasi bugungi yoki kelajakdagi sana bo'lishi kerak")
    private LocalDate startDate;

    @NotNull(message = "Tugash sanasi bo'sh bo'lmasligi kerak")
    @Future(message = "Tugash kelajakdagi sana bo'lishi kerak")
    private LocalDate endDate;

    private String comment;
}
