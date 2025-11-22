package uz.hrmanager.hrmanager.dto;

import lombok.Getter;
import lombok.Setter;
import uz.hrmanager.hrmanager.enums.Department;
import uz.hrmanager.hrmanager.enums.Position;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeResponseDTO {

    private Integer id;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Position position;
    private Department department;
    private LocalDate hireDate;
    private Boolean active;
}