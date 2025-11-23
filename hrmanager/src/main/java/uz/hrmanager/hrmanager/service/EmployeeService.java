package uz.hrmanager.hrmanager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.hrmanager.hrmanager.dto.EmployeeCreateDTO;
import uz.hrmanager.hrmanager.dto.EmployeeResponseDTO;
import uz.hrmanager.hrmanager.dto.EmployeeUpdateDTO;
import uz.hrmanager.hrmanager.entity.EmployeeEntity;
import uz.hrmanager.hrmanager.entity.LeaveBalanceEntity;
import uz.hrmanager.hrmanager.enums.Department;
import uz.hrmanager.hrmanager.enums.Position;
import uz.hrmanager.hrmanager.exceptions.AppBadException;
import uz.hrmanager.hrmanager.repository.EmployeeRepository;
import uz.hrmanager.hrmanager.repository.LeaveBalanceRepository;
import uz.hrmanager.hrmanager.service.mapper.EmployeeMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private LeaveBalanceService leaveBalanceService;


    // CREATE
    public EmployeeResponseDTO create(EmployeeCreateDTO dto) {

        // checking phone
        boolean phoneExists = employeeRepository.existsByPhoneAndActiveTrue(dto.getPhone());
        if (phoneExists) {
            throw new AppBadException("Kiritilgan telefon raqami bazada mavjud va boshqa xodimga tegishli.");
        }
        // checking Position (RAXBAR)
        if (dto.getPosition().equals(Position.RAXBAR)) {

            boolean raxbarExists = employeeRepository.existsByPositionAndActiveTrue(Position.RAXBAR);

            if (raxbarExists) {
                throw new AppBadException("Tizimda allaqachon faol 'RAXBAR' mavjud. Faqat bitta RAXBAR bo'lishi mumkin.");
            }
        }
        EmployeeEntity entity = employeeMapper.toEntity(dto);
        EmployeeEntity saved = employeeRepository.save(entity);

        // TA'TIL BALANSINI AVTOMATIK YARATISH
        leaveBalanceService.initializeLeaveBalance(saved);

        return employeeMapper.toResponseDTO(saved);
    }

    // barcha xodimlarni olib kelish
    public List<EmployeeResponseDTO> getAll() {
        List all = employeeRepository.findAllByActiveTrue();
        return all;
    }

    // bitta xodimni olish ID orqali
    public EmployeeResponseDTO getById(Integer id) {
        Optional<EmployeeEntity> optional = employeeRepository.findByIdAndActiveTrue(id);
        if(optional.isEmpty()){
            log.error("Profile not found: {}",id);
            throw new AppBadException("Profile not found");
        }
        return employeeMapper.toResponseDTO(optional.get());
    }

    // UPDATE
    public EmployeeResponseDTO update(Integer id, EmployeeUpdateDTO dto) {
        Optional<EmployeeEntity> optional = employeeRepository.findByIdAndActiveTrue(id);
        log.info("Bazadagi iiiiiiiiddddddddddd------ {}", optional.get().getId());
        // check
        if (optional.isEmpty()){
            log.error("Employee not found by ID-{}",id);
            throw new AppBadException("Employee not found by ID");
        }
        // checking phone
        if (!optional.get().getPhone().equals(dto.getPhone())) {

            Optional<EmployeeEntity> byPhone = employeeRepository.findByPhoneAndIdNot(dto.getPhone(), id);

            if (byPhone.isPresent()) {
                log.info("Boshqa xodim topildiiiiiii----IDsi-- {}", optional.get().getId());
                // Agar boshqa xodim topilsa
                throw new AppBadException("Kiritilgan telefon raqami boshqa faol xodim tomonidan allaqachon ishlatilmoqda.");
            }
        }

        EmployeeEntity entity = employeeMapper.updateEntityFromDto(dto, optional.get());
        EmployeeEntity saved = employeeRepository.save(entity);
        return employeeMapper.toResponseDTO(saved);
    }

    // DELETE
    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }

    // -------- Filter va search qismlari --------

    // Ism/familiya bo'yicha qidirish
    public List<EmployeeResponseDTO> searchAllByName(String name) {
        List<EmployeeEntity> entities = employeeRepository.searchAllByName(name);

        List<EmployeeResponseDTO> result = new ArrayList<>();
        for (EmployeeEntity entity : entities) {
            EmployeeResponseDTO dto = employeeMapper.toResponseDTO(entity);
            result.add(dto);
        }

        return result;
    }

    // Lavozim bo'yicha filter
    public List<EmployeeResponseDTO> filterByPosition(Position position) {
        List<EmployeeEntity> entities = employeeRepository.findByPosition(position.name());

        List<EmployeeResponseDTO> result = new ArrayList<>();
        for (EmployeeEntity entity : entities) {
            EmployeeResponseDTO dto = employeeMapper.toResponseDTO(entity);
            result.add(dto);
        }

        return result;
    }

    // Bo'lim bo'yicha filter
    public List<EmployeeResponseDTO> filterByDepartment(Department department) {
        List<EmployeeEntity> entities = employeeRepository.findByDepartment(department.name());
        List<EmployeeResponseDTO> result = new ArrayList<>();
        for (EmployeeEntity entity : entities) {
            EmployeeResponseDTO dto = employeeMapper.toResponseDTO(entity);
            result.add(dto);
        }

        return result;
    }


    // Active bo'yicha filter
    public List<EmployeeResponseDTO> filterByActive(boolean active) {
        Optional<EmployeeEntity> result = employeeRepository.findByActive(active);
        return toResponseList(result);
    }



    // Optional<Entity> -> List<ResponseDTO>
    private List<EmployeeResponseDTO> toResponseList(Optional<EmployeeEntity> optionalEntity) {
        List<EmployeeResponseDTO> result = new ArrayList<>();

        if (optionalEntity.isPresent()) {
            EmployeeEntity entity = optionalEntity.get();
            EmployeeResponseDTO dto = employeeMapper.toResponseDTO(entity);
            result.add(dto);
        }

        return result;
    }



}
