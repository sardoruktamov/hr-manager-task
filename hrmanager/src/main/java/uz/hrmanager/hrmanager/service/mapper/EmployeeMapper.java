package uz.hrmanager.hrmanager.service.mapper;
import org.springframework.stereotype.Component;
import uz.hrmanager.hrmanager.dto.EmployeeCreateDTO;
import uz.hrmanager.hrmanager.dto.EmployeeResponseDTO;
import uz.hrmanager.hrmanager.dto.EmployeeUpdateDTO;
import uz.hrmanager.hrmanager.entity.EmployeeEntity;

import java.time.LocalDate;

@Component
public class EmployeeMapper {

    // RequestDTO dan Entityga o'girish (CREATE uchun)
    public EmployeeEntity toEntity(EmployeeCreateDTO dto) {
        if (dto == null) return null;

        EmployeeEntity entity = new EmployeeEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPosition(dto.getPosition());
        entity.setDepartment(dto.getDepartment());
        entity.setHireDate(LocalDate.now());
        entity.setActive(true);
        return entity;
    }

    // Entity dan ResponseDTOga o'girish
    public EmployeeResponseDTO toResponseDTO(EmployeeEntity entity) {
        if (entity == null) return null;

        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setPosition(entity.getPosition());
        dto.setDepartment(entity.getDepartment());
        dto.setHireDate(entity.getHireDate());
        dto.setActive(entity.getActive());
        return dto;
    }

    // UPDATE uchun- mavjud Entity ichiga RequestDTOdan fieldlarni yozish
    public EmployeeEntity updateEntityFromDto(EmployeeUpdateDTO dto, EmployeeEntity entity) {
        if (dto == null || entity == null) return entity;

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPosition(dto.getPosition());
        entity.setDepartment(dto.getDepartment());
        entity.setHireDate(LocalDate.now());
        entity.setActive(dto.getActive());
        return entity;
    }
}

