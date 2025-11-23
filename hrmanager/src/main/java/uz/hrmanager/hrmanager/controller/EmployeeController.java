package uz.hrmanager.hrmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.hrmanager.hrmanager.dto.EmployeeCreateDTO;
import uz.hrmanager.hrmanager.dto.EmployeeResponseDTO;
import uz.hrmanager.hrmanager.dto.EmployeeUpdateDTO;
import uz.hrmanager.hrmanager.entity.LeaveBalanceEntity;
import uz.hrmanager.hrmanager.enums.Department;
import uz.hrmanager.hrmanager.enums.Position;
import uz.hrmanager.hrmanager.repository.LeaveBalanceRepository;
import uz.hrmanager.hrmanager.service.EmployeeService;

import java.util.List;

import static uz.hrmanager.hrmanager.config.SwaggerConstants.*;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @Operation(description = "YANGI EMPLOYEE YARATISH",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "EMPLOYEE YARATISH UCHUN POST REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(value = CREATE_EMPLOYEE_EXAMPLE)
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OOK!",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(value = CREATE_EMPLOYEE_RESPONSE_SUCCESS_EXAMPLE)
                    }
            ))
        }
    )
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> create( @RequestBody @Valid EmployeeCreateDTO dto) {
        return ResponseEntity.ok(employeeService.create(dto));
    }


    @GetMapping
    @Operation(description = "BARCHA XODIMLARNI MA'LUMOTLARINI QAYTARISH")
    public ResponseEntity<List<EmployeeResponseDTO>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @Operation(description = "ID BO'YICHA XODIM MA'LUMOTLARINI QAYTARISH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Muvaffaqiyatli topildi (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(value = GET_EMPLOYEE_RESPONSE_SUCCESS_EXAMPLE)
                            }
                    )),
            @ApiResponse(responseCode = "404", description = "Xodim ID si topilmadi")
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getById(
            @Parameter(description = "Qidirilayotgan xodim ID si", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @Operation(description = "EMPLOYEE MA'LUMOTLARINI YANGILASH",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "EMPLOYEE YANGILASH UCHUN PUT REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(value = UPDATE_EMPLOYEE_EXAMPLE)
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Muvaffaqiyatli yangilandi (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(value = UPDATE_EMPLOYEE_RESPONSE_SUCCESS_EXAMPLE)
                            }
                    )),
            @ApiResponse(responseCode = "404", description = "Xodim topilmadi")
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> update(
            @Parameter(description = "Yangilanadigan xodim ID si", required = true)
            @PathVariable Integer id,
            @RequestBody @Valid EmployeeUpdateDTO dto
    ) {
        return ResponseEntity.ok(employeeService.update(id,dto));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "ID BO'YICHA XODIM MA'LUMOTLARINI O'CHIRISH")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search-all")
    @Operation(description = "ISM YOKI FAMILYA BO'YICHA XODIM MA'LUMOTLARINI QAYTARISH")
    public ResponseEntity<List<EmployeeResponseDTO>> searchByName(@RequestParam("name") String name) {
        List<EmployeeResponseDTO> result = employeeService.searchAllByName(name);

        if (result.isEmpty()) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter-by-position")
    @Operation(description = "LAVOZIM BO'YICHA XODIM MA'LUMOTLARINI QAYTARISH")
    public ResponseEntity<List<EmployeeResponseDTO>> filterByPosition(@RequestParam("position") Position position) {
        List<EmployeeResponseDTO> result = employeeService.filterByPosition(position);
        if (result.isEmpty()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter-by-department")
    @Operation(description = "BO'LIM BO'YICHA XODIM MA'LUMOTLARINI QAYTARISH")
    public ResponseEntity<List<EmployeeResponseDTO>> filterByPosition(@RequestParam("department") Department department) {
        List<EmployeeResponseDTO> result = employeeService.filterByDepartment(department);
        if (result.isEmpty()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(result);
    }


}

