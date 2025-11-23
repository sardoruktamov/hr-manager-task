package uz.hrmanager.hrmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.hrmanager.hrmanager.dto.LeaveRequestCreateDTO;
import uz.hrmanager.hrmanager.dto.LeaveRequestResponseDTO;
import uz.hrmanager.hrmanager.entity.LeaveBalanceEntity;
import uz.hrmanager.hrmanager.service.LeaveRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leaves")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    // ARIZA YUBORISH (Xodim tomonidan)
    @PostMapping
    public ResponseEntity<LeaveRequestResponseDTO> create(
            @RequestBody @Valid LeaveRequestCreateDTO dto,
            // Header nomini to'g'ridan-to'g'i ishlatamiz
            @RequestHeader("X-Auth-User-Id") Integer employeeId) {

        return ResponseEntity.ok(leaveRequestService.create(dto, employeeId));
    }

    @GetMapping("/pending")
    @Operation(description = "RAXBAR UCHUN TASDIQLASHGA KUTILAYOTGAN ARIZALAR RO'YXATINI QAYTARISH")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getPendingRequests(
            @RequestHeader("X-Auth-User-Id") Integer managerId) {

        return ResponseEntity.ok(leaveRequestService.getPendingRequestsForManager(managerId));
    }

    // ARIZANI TASDIQLASH (RAXBAR tomonidan)
    @PutMapping("/{requestId}/approve")
    public ResponseEntity<LeaveRequestResponseDTO> approveRequest(
            @PathVariable Integer requestId,
            @RequestHeader("X-Auth-User-Id") Integer managerId) {

        return ResponseEntity.ok(leaveRequestService.approve(requestId, managerId));
    }

    // ARIZANI RAD ETISH (RAXBAR tomonidan)
    @PutMapping("/{requestId}/reject")
    public ResponseEntity<LeaveRequestResponseDTO> rejectRequest(
            @PathVariable Integer requestId,
            @RequestHeader("X-Auth-User-Id") Integer managerId,
            @RequestBody String managerComment) {

        return ResponseEntity.ok(leaveRequestService.reject(requestId, managerId, managerComment));
    }

    @GetMapping("/my-requests")
    @Operation(description = "XODIMGA TEGISHLI BARCHA ARIZALAR RO'YXATINI QAYTARISH")
    public ResponseEntity<List<LeaveRequestResponseDTO>> getEmployeeRequests(
            @RequestHeader("X-Auth-User-Id") Integer employeeId) {
         return ResponseEntity.ok(leaveRequestService.getEmployeeRequests(employeeId));
    }

    @GetMapping("/{id}/balance")
    @Operation(description = "XODIMNING TATIL BALANSI MALUMOTINI QAYTARISH")
    public ResponseEntity<List<LeaveBalanceEntity>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(leaveRequestService.getEmployeeBalances(id));
    }
}