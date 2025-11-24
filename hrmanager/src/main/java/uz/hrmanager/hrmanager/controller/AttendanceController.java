package uz.hrmanager.hrmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.hrmanager.hrmanager.dto.AttendanceCreateDTO;
import uz.hrmanager.hrmanager.dto.AttendanceReportDTO;
import uz.hrmanager.hrmanager.service.AttendanceService;

import java.time.LocalDate;
import java.util.List;

import static uz.hrmanager.hrmanager.config.SwaggerConstants.*;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Operation(description = "BU QISM TURNIKET (yoki boshqa) ISHLAMAGAN KUNI UCHUN BIR VAQTDA ISHGA KELISH/KETISHNI YOZISH",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "BIR KUNLIK YARATISH POST REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(value = CREATE_ATTANDANCE_EXAMPLE)
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OOK!",content = @Content(
                    examples = {
                            @ExampleObject(value = CREATE_ATTANDANCE_RESPONSE_SUCCESS_EXAMPLE)
                    }
            ))
    }
    )
    @PostMapping("/set-by-hr/{employeeId}")
    public ResponseEntity<String> hrSetAttendance(
            @Parameter(description = "Xodim ID si", required = true)
            @PathVariable Integer employeeId,
            @RequestBody @Valid AttendanceCreateDTO dto) {

        attendanceService.hrSetAttendance(employeeId, dto);
        return ResponseEntity.ok(
                "Xodimning ishga kelish/ketish vaqti muvaffaqiyatli belgilandi. Status: " +
                        attendanceService.determineStatus(dto.getInTime()));
    }

    @PostMapping("/check-in")
    @Operation(description = "XODIMNI ISHGA KELGAN VAQTINI KIRITISH (SERVER VAQTI BILAN)")
    public ResponseEntity<String> checkIn(
            @Parameter(description = "Ishga kelgan xodim ID sini kiriting", required = true)
            @RequestHeader("X-Auth-User-Id") Integer employeeId) {
        // Vaqtni avtomatik serverdan olamiz

        attendanceService.checkIn(employeeId);
        return ResponseEntity.ok("Xodim ID-" + employeeId + " ishga muvaffaqiyatli keldi.");
    }

    @PutMapping("/check-out")
    @Operation(description = "XODIMNI ISHDAN KETGAN VAQTINI KIRITISH (SERVER VAQTI BILAN)")
    public ResponseEntity<String> checkOut(
            @Parameter(description = "Ishga kelgan xodim ID sini kiriting", required = true)
            @RequestHeader("X-Auth-User-Id") Integer employeeId) {

        attendanceService.checkOut(employeeId);
        return ResponseEntity.ok("Xodim ID-" + employeeId + " ishdan muvaffaqiyatli ketdi.");
    }

    @GetMapping("/report/lateness-absence")
    @Operation(description = "HISOBOT OLISH:KECHIKKANLAR VA KELMAGANLAR RO'YXATINI QAYTARISH")
    public ResponseEntity<List<AttendanceReportDTO>> getLatenessAndAbsenceReport(
            @Parameter(description = "BOSHLANG'ICH SANANI KIRITING", required = true)
            @RequestParam("startdate") LocalDate startDate,
            @Parameter(description = "TUGASH SANANI KIRITING", required = true)
            @RequestParam("enddate") LocalDate endDate) {

        List<AttendanceReportDTO> report = attendanceService.getLateAndAbsenceReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/report/between-date")
    @Operation(description = "HISOBOT OLISH:IKKITA SANA ORALIG'IDA ISHGA KELGAN XODIMLARNI RO'YXATINI QAYTARISH")
    public ResponseEntity<List<AttendanceReportDTO>> getWorkDateBetweenReport(
            @Parameter(description = "BOSHLANG'ICH SANANI KIRITING", required = true)
            @RequestParam("startdate") LocalDate startDate,
            @Parameter(description = "TUGASH SANANI KIRITING", required = true)
            @RequestParam("enddate") LocalDate endDate
    ){
        List<AttendanceReportDTO> report = attendanceService.getWorkDateBetween(startDate, endDate);
        return ResponseEntity.ok(report);
    }
}
