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
import uz.hrmanager.hrmanager.dto.LeaveRequestCreateDTO;
import uz.hrmanager.hrmanager.dto.LeaveRequestResponseDTO;
import uz.hrmanager.hrmanager.entity.LeaveBalanceEntity;
import uz.hrmanager.hrmanager.service.LeaveRequestService;

import java.util.List;

import static uz.hrmanager.hrmanager.config.SwaggerConstants.*;

@RestController
@RequestMapping("/api/v1/leaves")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    // ARIZA YUBORISH (Xodim tomonidan)
    @Operation(description = "TA'TIL UCHUN YANGI ARIZA YARATISH",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "TA'TIL UCHUN ARIZA YARATISH POST REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(value = CREATE_LEAVEREQUEST_EXAMPLE)
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OOK!",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(value = CREATE_LEAVEREQUEST_RESPONSE_SUCCESS_EXAMPLE)
                    }
            ))
    }
    )
    @PostMapping
    public ResponseEntity<LeaveRequestResponseDTO> create(
            @RequestBody @Valid LeaveRequestCreateDTO dto,
            @Parameter(description = "Ariza yuborayotgan xodim ID si", required = true)
            @RequestHeader("X-Auth-User-Id") Integer employeeId) {

        return ResponseEntity.ok(leaveRequestService.create(dto, employeeId));
    }

    @GetMapping("/pending")
    @Operation(description = "RAXBAR UCHUN TASDIQLASHGA KUTILAYOTGAN ARIZALAR RO'YXATINI QAYTARISH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OOK!",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(value = CREATE_LISTLEAVEREQUEST_RESPONSE_SUCCESS_EXAMPLE)
                    }
            ))
    }
    )
    public ResponseEntity<List<LeaveRequestResponseDTO>> getPendingRequests(
            @Parameter(description = "Raxbar ID si", required = true)
            @RequestHeader("X-Auth-User-Id") Integer managerId) {

        return ResponseEntity.ok(leaveRequestService.getPendingRequestsForManager(managerId));
    }

    @Operation(description = "TA'TIL UCHUN BERILGAN ARIZANI TASDIQLASH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OOK!",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(value = APPROVE_LEAVEREQUEST_RESPONSE_SUCCESS_EXAMPLE)
                    }
            ))
    }
    )
    @PutMapping("/{requestId}/approve")
    public ResponseEntity<LeaveRequestResponseDTO> approveRequest(
            @Parameter(description = "Xodimning arizasi ID si", required = true)
            @PathVariable Integer requestId,
            @Parameter(description = "Tasdiqlash uchun Raxbar ID si", required = true)
            @RequestHeader("X-Auth-User-Id") Integer managerId) {

        return ResponseEntity.ok(leaveRequestService.approve(requestId, managerId));
    }

    @Operation(description = "TA'TIL UCHUN BERILGAN ARIZANI RAD QILISH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OOK!",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(value = REJECT_LEAVEREQUEST_RESPONSE_SUCCESS_EXAMPLE)
                    }
            ))
    }
    )
    @PutMapping("/{requestId}/reject")
    public ResponseEntity<LeaveRequestResponseDTO> rejectRequest(
            @Parameter(description = "Xodimning ta'til arizasi ID si", required = true)
            @PathVariable Integer requestId,
            @Parameter(description = "Tasdiqlash uchun Raxbar ID si", required = true)
            @RequestHeader("X-Auth-User-Id") Integer managerId,
            @Parameter(description = "Arizani RAD etish sababi:", required = true)
            @RequestBody String managerComment) {

        return ResponseEntity.ok(leaveRequestService.reject(requestId, managerId, managerComment));
    }

    @GetMapping("/my-requests")
    @Operation(description = "XODIMGA TEGISHLI BARCHA ARIZALAR RO'YXATINI QAYTARISH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Muvaffaqiyatli topildi (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(value = GET_LEAVEREQUEST_RESPONSE_SUCCESS_EXAMPLE)
                            }
                    )),
            @ApiResponse(responseCode = "404", description = "Xodim ID si topilmadi")
    }
    )
    public ResponseEntity<List<LeaveRequestResponseDTO>> getEmployeeRequests(
            @Parameter(description = "Qidirilayotgan xodim ID si", required = true)
            @RequestHeader("X-Auth-User-Id") Integer employeeId) {
         return ResponseEntity.ok(leaveRequestService.getEmployeeRequests(employeeId));
    }

    @GetMapping("/{id}/balance")
    @Operation(description = "XODIMNING TATIL BALANSI MALUMOTINI QAYTARISH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Muvaffaqiyatli topildi (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(value = GET_BALANCE_RESPONSE_SUCCESS_EXAMPLE)
                            }
                    )),
            @ApiResponse(responseCode = "404", description = "Xodim ID si topilmadi")
    }
    )
    public ResponseEntity<List<LeaveBalanceEntity>> getById(
            @Parameter(description = "Qidirilayotgan xodim ID si", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(leaveRequestService.getEmployeeBalances(id));
    }
}