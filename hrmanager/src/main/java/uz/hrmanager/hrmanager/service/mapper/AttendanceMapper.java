package uz.hrmanager.hrmanager.service.mapper;

import org.springframework.stereotype.Component;
import uz.hrmanager.hrmanager.dto.AttendanceReportDTO;
import uz.hrmanager.hrmanager.entity.AttendanceEntity;
import uz.hrmanager.hrmanager.enums.AttendanceStatus;

import java.time.Duration;
import java.time.LocalTime;

@Component
public class AttendanceMapper {

    // Majburiy ish vaqti 8 soat, 1 soat obed uchun qilib belglandi
    private static final LocalTime REQUIRED_IN_TIME = LocalTime.of(9, 0);

    // --- Kechikishni hisoblash ---
    private Long calculateLateMinutes(LocalTime inTime) {
        // 09:00 dan keyin kelganmi?
        if (inTime != null && inTime.isAfter(REQUIRED_IN_TIME)) {
            return Duration.between(REQUIRED_IN_TIME, inTime).toMinutes();
        }
        return 0L;
    }

    // --- Entity -> ReportDTO ---
    public AttendanceReportDTO toReportDTO(AttendanceEntity entity) {
        AttendanceReportDTO dto = new AttendanceReportDTO();
        dto.setId(entity.getId());
        dto.setWorkDate(entity.getWorkDate());
        dto.setInTime(entity.getInTime());
        dto.setOutTime(entity.getOutTime());
        dto.setStatus(entity.getStatus());
        dto.setComment(entity.getComment());

        // Employee ma'lumotlari
        if (entity.getEmployee() != null) {
            dto.setEmployeeFullName(entity.getEmployee().getFirstName() + " " + entity.getEmployee().getLastName());
        }

        // Kechikish daqiqalarini hisoblash (Faqat LATE bo'lsa)
        if (entity.getStatus() == AttendanceStatus.LATE) {
            dto.setLateMinutes(calculateLateMinutes(entity.getInTime()));
        } else {
            dto.setLateMinutes(0L);
        }

        return dto;
    }
}
