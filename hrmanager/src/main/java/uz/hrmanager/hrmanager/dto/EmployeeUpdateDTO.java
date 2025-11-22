package uz.hrmanager.hrmanager.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uz.hrmanager.hrmanager.enums.Department;
import uz.hrmanager.hrmanager.enums.Position;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeUpdateDTO {

    @NotBlank(message = "Ism bo‘sh bo‘lmasligi kerak")
    private String firstName;
    @NotBlank(message = "Familiya bo‘sh bo‘lmasligi kerak")
    private String lastName;
    @NotBlank(message = "Telefon nomer bo‘sh bo‘lmasligi kerak")
    private String phone;
    private String email;
    @NotNull
    private Position position;
    @NotNull
    private Department department;
    private Boolean active; // agar ishdan boshasa

}

