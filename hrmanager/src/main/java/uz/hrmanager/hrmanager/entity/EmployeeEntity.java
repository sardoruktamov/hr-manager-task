package uz.hrmanager.hrmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.hrmanager.hrmanager.enums.Department;
import uz.hrmanager.hrmanager.enums.Position;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "department")
    @Enumerated(EnumType.STRING)
    private Department department;

    @Column(name = "hire_date")
    private LocalDate hireDate; // ishga kirgan kuni

    private Boolean active; // ishda yoki ketgan
}

