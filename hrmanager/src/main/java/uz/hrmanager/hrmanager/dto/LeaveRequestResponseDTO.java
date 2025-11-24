package uz.hrmanager.hrmanager.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uz.hrmanager.hrmanager.enums.LeaveStatus;
import uz.hrmanager.hrmanager.enums.LeaveType;

import java.time.LocalDate;

@Getter
@Setter
public class LeaveRequestResponseDTO {
    private Integer id;
    private String requestContent;

    // Xodim ma'lumotlari
    private String employeeFirstName;
    private String employeeLastName;

    // Raxbar ma'lumotlari
    private String managerFullName;

    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalDays;
    private LeaveStatus status;
    private String managerComment;
}
