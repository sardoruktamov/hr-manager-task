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
    private Integer id;
    private String requestContent;

    // Kim tomonidan yuborilgan
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    // Kim tomonidan tasdiqlanishi
    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private EmployeeEntity manager;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "total_days")
    private Integer totalDays; // tatilda bolgan kun

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    private String managerComment;
}

