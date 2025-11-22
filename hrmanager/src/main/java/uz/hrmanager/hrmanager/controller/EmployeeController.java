package uz.hrmanager.hrmanager.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    // CREATE
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> create( @RequestBody @Valid EmployeeCreateDTO dto) {
        return ResponseEntity.ok(employeeService.create(dto));
    }

    //  barcha xodimlarni olib kelish
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    // bitta xodimni olish
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> update(
            @PathVariable Integer id,
            @RequestBody @Valid EmployeeUpdateDTO dto
    ) {
        return ResponseEntity.ok(employeeService.update(id,dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Ism yoki familiya bo‘yicha qidirish
    @GetMapping("/search-all")
    public ResponseEntity<List<EmployeeResponseDTO>> searchByName(@RequestParam("name") String name) {
        List<EmployeeResponseDTO> result = employeeService.searchAllByName(name);

        if (result.isEmpty()) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.ok(result);
    }

    // lavozim bo‘yicha filterlash
    @GetMapping("/filter-by-position")
    public ResponseEntity<List<EmployeeResponseDTO>> filterByPosition(@RequestParam("position") Position position) {
        List<EmployeeResponseDTO> result = employeeService.filterByPosition(position);
        if (result.isEmpty()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(result);
    }

    // bo'lim bo‘yicha filterlash
    @GetMapping("/filter-by-department")
    public ResponseEntity<List<EmployeeResponseDTO>> filterByPosition(@RequestParam("department") Department department) {
        List<EmployeeResponseDTO> result = employeeService.filterByDepartment(department);
        if (result.isEmpty()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(result);
    }


}

