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

    private String firstName;
    private String lastName;

    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private Department department;

    private LocalDate hireDate; // ishga kirgan kuni

    private Boolean active; // ishda yoki ketgan
}

