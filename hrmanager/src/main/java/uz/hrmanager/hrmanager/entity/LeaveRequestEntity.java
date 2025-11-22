package uz.hrmanager.hrmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.hrmanager.hrmanager.enums.LeaveStatus;
import uz.hrmanager.hrmanager.enums.LeaveType;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "leave_request")
public class LeaveRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kim tomonidan yuborilgan
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    private LocalDate startDate;
    private LocalDate endDate;

    private Integer totalDays; // tatilda bolgan kun

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    private String managerComment;
}

