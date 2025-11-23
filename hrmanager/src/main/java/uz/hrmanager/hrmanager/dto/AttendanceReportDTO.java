package uz.hrmanager.hrmanager.dto;

import lombok.Getter;
import lombok.Setter;
import uz.hrmanager.hrmanager.enums.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AttendanceReportDTO {
    private Long id;
    private String employeeFullName;
    private LocalDate workDate;
    private LocalTime inTime;
    private LocalTime outTime;
    private AttendanceStatus status;
    private Long lateMinutes; // Kechikish daqiqalari (agar status LATE bo'lsa)
    private String comment;
}
