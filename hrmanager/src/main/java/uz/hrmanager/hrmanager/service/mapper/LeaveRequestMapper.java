package uz.hrmanager.hrmanager.service.mapper;


import org.springframework.stereotype.Component;
import uz.hrmanager.hrmanager.dto.LeaveRequestResponseDTO;
import uz.hrmanager.hrmanager.entity.LeaveRequestEntity;

@Component
public class LeaveRequestMapper {

    public LeaveRequestResponseDTO toResponseDTO(LeaveRequestEntity entity) {
        LeaveRequestResponseDTO dto = new LeaveRequestResponseDTO();
        dto.setId(entity.getId());
        dto.setRequestContent(entity.getRequestContent());
        dto.setLeaveType(entity.getLeaveType());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setTotalDays(entity.getTotalDays());
        dto.setStatus(entity.getStatus());

        // Employee ma'lumotlari
        if (entity.getEmployee() != null) {
            dto.setEmployeeFirstName(entity.getEmployee().getFirstName());
            dto.setEmployeeLastName(entity.getEmployee().getLastName());
        }

        // Raxbar ma'lumotlari
        if (entity.getManager() != null) {
            dto.setManagerFullName(entity.getManager().getFirstName() + " " + entity.getManager().getLastName());
        }

        return dto;
    }

}
