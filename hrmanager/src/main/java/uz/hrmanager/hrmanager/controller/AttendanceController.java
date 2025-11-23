package uz.hrmanager.hrmanager.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.hrmanager.hrmanager.dto.AttendanceCreateDTO;
import uz.hrmanager.hrmanager.dto.AttendanceReportDTO;
import uz.hrmanager.hrmanager.service.AttendanceService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // kirish/Chiqishni Belgilash
    @PostMapping("/set-by-hr/{employeeId}")
    public ResponseEntity<String> hrSetAttendance(
            @PathVariable Integer employeeId,
            @RequestBody @Valid AttendanceCreateDTO dto) {

        attendanceService.hrSetAttendance(employeeId, dto);
        return ResponseEntity.ok(
                "Xodimning ishga kelish/ketish vaqti muvaffaqiyatli belgilandi. Status: " +
                        attendanceService.determineStatus(dto.getInTime()));
    }

    // Ishga Kelish (Check-In)
    @PostMapping("/check-in")
    public ResponseEntity<String> checkIn(
            @RequestHeader("X-Auth-User-Id") Integer employeeId) {
        // Vaqtni avtomatik serverdan olamiz

        attendanceService.checkIn(employeeId);
        return ResponseEntity.ok("Xodim ID-" + employeeId + " ishga muvaffaqiyatli keldi.");
    }

    // Ishdan Ketish (Check-Out)
    @PutMapping("/check-out")
    public ResponseEntity<String> checkOut(
            @RequestHeader("X-Auth-User-Id") Integer employeeId) {

        attendanceService.checkOut(employeeId);
        return ResponseEntity.ok("Xodim ID-" + employeeId + " ishdan muvaffaqiyatli ketdi.");
    }

    // HISOBOT OLISH: Kechikishlar va Kelmaganlar ro'yxatini olish
    @GetMapping("/report/lateness-absence")
    public ResponseEntity<List<AttendanceReportDTO>> getLatenessAndAbsenceReport(
            @RequestParam("startdate") LocalDate startDate,
            @RequestParam("enddate") LocalDate endDate) {

        List<AttendanceReportDTO> report = attendanceService.getLateAndAbsenceReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    // ikkita sana oraligini hisobotini olish
    @GetMapping("/report/between-date")
    public ResponseEntity<List<AttendanceReportDTO>> getWorkDateBetweenReport(
            @RequestParam("startdate") LocalDate startDate,
            @RequestParam("enddate") LocalDate endDate
    ){
        List<AttendanceReportDTO> report = attendanceService.getWorkDateBetween(startDate, endDate);
        return ResponseEntity.ok(report);
    }
}
