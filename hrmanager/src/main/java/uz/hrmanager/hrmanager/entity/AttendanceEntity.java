package uz.hrmanager.hrmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.hrmanager.hrmanager.enums.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "attendance")
public class AttendanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    private LocalDate workDate;           // ish kuni
    private LocalTime InTime;
    private LocalTime OutTime;

    // o'z vaqtida, kechikkan, yo'qligi
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AttendanceStatus status;

    // Qo'shimcha izoh, kechikish sababi yozish
    @Column(name = "comment")
    private String comment;
}

