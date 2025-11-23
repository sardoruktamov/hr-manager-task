package uz.hrmanager.hrmanager.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.hrmanager.hrmanager.entity.EmployeeEntity;
import uz.hrmanager.hrmanager.entity.LeaveBalanceEntity;
import uz.hrmanager.hrmanager.entity.LeaveRequestEntity;
import uz.hrmanager.hrmanager.enums.LeaveStatus;
import uz.hrmanager.hrmanager.enums.Position;
import uz.hrmanager.hrmanager.exceptions.AppBadException;
import uz.hrmanager.hrmanager.repository.EmployeeRepository;
import uz.hrmanager.hrmanager.repository.LeaveBalanceRepository;
import uz.hrmanager.hrmanager.repository.LeaveRequestRepository;
import uz.hrmanager.hrmanager.dto.LeaveRequestCreateDTO;
import uz.hrmanager.hrmanager.dto.LeaveRequestResponseDTO;
import uz.hrmanager.hrmanager.service.mapper.LeaveRequestMapper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private LeaveBalanceService leaveBalanceService;
    @Autowired
    private LeaveRequestMapper leaveRequestMapper;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;


    // ARIZA YUBORISH (CREATE)
    public LeaveRequestResponseDTO create(LeaveRequestCreateDTO dto, Integer employeeId) {
        // Arizani yuborayotgan xodimni topish
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new AppBadException("Xodim topilmadi."));

        // Raxbarni topish (arizani faqat RAXBAR tasdiqlashi kerak)
        EmployeeEntity raxbar = employeeRepository.findByPositionAndActiveTrue(Position.RAXBAR)
                .orElseThrow(() -> new AppBadException("Tizimda RAXBAR topilmadi."));

        // Kunlar sonini hisoblash
        Integer totalDays = calculateTotalDays(dto.getStartDate(), dto.getEndDate());

        // Balansni tekshirish
        if (!leaveBalanceService.isBalanceSufficient(employeeId, dto.getLeaveType(), totalDays)) {
            throw new AppBadException(dto.getLeaveType() + " ta'til turi bo'yicha qolgan kunlar yetarli emas.");
        }

        // Entity yaratish
        LeaveRequestEntity entity = new LeaveRequestEntity();
        entity.setEmployee(employee);
        entity.setRequestContent(dto.getRequestContent());
        entity.setManager(raxbar); // RAXBAR ga yo'naltiramiz
        entity.setLeaveType(dto.getLeaveType());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setTotalDays(totalDays);
        entity.setStatus(LeaveStatus.JARAYONDA);

        LeaveRequestEntity saved = leaveRequestRepository.save(entity);
        return leaveRequestMapper.toResponseDTO(saved);
    }

    // tatil kunlarni hisoblash
    private Integer calculateTotalDays(LocalDate start, LocalDate end) {
        // Ta'til kunlarini hisoblash (Start va End kunlari ham kiradi)
        if (start.isAfter(end)) {
            throw new AppBadException("Boshlanish sanasi tugash sanasidan oldin bo'lishi kerak.");
        }
        // kunlar soni + 1 (agar bir kun bo'lsa, ChronoUnit.DAYS 0 qaytaradi shuning uchun 1 qo'shamiz)
        return (int) ChronoUnit.DAYS.between(start, end) + 1;
    }
    // ARIZANI TASDIQLASH
    public LeaveRequestResponseDTO approve(Integer requestId, Integer managerId) {
        // Tasdiqlovchi Raxbarmi yoki boshqaligini tekshirish
        EmployeeEntity manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new AppBadException("Tasdiqlovchi xodim topilmadi."));

        // Faqat RAXBAR tasdiqlay oladi
        if (!manager.getPosition().equals(Position.RAXBAR)) {
            throw new AppBadException("Sizda arizani tasdiqlash uchun vakolat yo'q. Faqat RAXBAR tasdiqlay oladi.");
        }

        // Arizani topish va tekshirish
        LeaveRequestEntity request = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new AppBadException("Ta'til arizasi topilmadi."));

        if (request.getStatus() != LeaveStatus.JARAYONDA) {
            throw new AppBadException("Faqat JARAYONDA turgan arizalar tasdiqlanadi.");
        }

        // Statusni yangilash (arizani tasdiqlash qismi)
        request.setStatus(LeaveStatus.TASDIQLANGAN);
        request.setManager(manager);

        // tatil balansini yangilash
        leaveBalanceService.updateBalanceOnApproval(
                request.getEmployee().getId(),
                request.getLeaveType(),
                request.getTotalDays()
        );

        LeaveRequestEntity saved = leaveRequestRepository.save(request);
        return leaveRequestMapper.toResponseDTO(saved);
    }

    //  ARIZANI RAD ETISH
    public LeaveRequestResponseDTO reject(Integer requestId, Integer managerId, String comment) {
        // Tasdiqlovchi Manager va RAXBARlikni tekshirish (oldingi usuldagidek)
        EmployeeEntity manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new AppBadException("Tasdiqlovchi xodim topilmadi."));

        if (!manager.getPosition().equals(Position.RAXBAR)) {
            throw new AppBadException("Sizda arizani rad etish uchun vakolat yo'q.");
        }

        LeaveRequestEntity request = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new AppBadException("Ta'til arizasi topilmadi."));

        if (request.getStatus() != LeaveStatus.JARAYONDA) {
            throw new AppBadException("Faqat JARAYONDA turgan arizalar rad etiladi.");
        }

        // Statusni yangilash
        request.setStatus(LeaveStatus.RAD_ETILGAN);
        request.setManagerComment(comment); // Rad etish sababini saqlash

        LeaveRequestEntity saved = leaveRequestRepository.save(request);
        // Rad etilganda balans o'zgarmaydi.
        return leaveRequestMapper.toResponseDTO(saved);
    }

    // RAXBAR UCHUN ARIZALAR RO'YXATINI KO'RISH
    public List<LeaveRequestResponseDTO> getPendingRequestsForManager(Integer managerId) {
        // Manager RAXBAR ekanligini tekshirish lozim, lekin hozir Repository usulidan foydalanamiz
        List<LeaveRequestEntity> entities = leaveRequestRepository.findAllByManagerIdAndStatus(
                managerId,
                LeaveStatus.JARAYONDA
        );
        return entities.stream()
                .map(entitie -> leaveRequestMapper.toResponseDTO(entitie))
                .collect(Collectors.toList());
    }

    //  Xodim uchun balansni ko'rish
    public List<LeaveBalanceEntity> getEmployeeBalances(Integer employeeId) {
        return leaveBalanceRepository.findAllByEmployeeId(employeeId);
    }

    public List<LeaveRequestResponseDTO> getEmployeeRequests(Integer employeeId) {
        List<LeaveRequestEntity> entities = leaveRequestRepository.findAllByEmployeeId(employeeId);
        return entities.stream()
                .map(leaveRequestEntity -> leaveRequestMapper.toResponseDTO(leaveRequestEntity))
                .collect(Collectors.toList());
    }
}
