package uz.hrmanager.hrmanager.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AttendanceCreateDTO {

    @NotNull(message = "Ish kui bo'sh bo'lmasligi kerak")
    private LocalDate workDate;

    // InTime va OutTime bo'sh qoldirilishi mumkin (masalan, kiritilmagan bo'lsa)
    private LocalTime inTime;
    private LocalTime outTime;
    // kech qolish sababi uchunizoh
    private String comment;
}
