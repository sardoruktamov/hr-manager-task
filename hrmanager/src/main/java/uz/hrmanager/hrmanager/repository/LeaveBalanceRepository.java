package uz.hrmanager.hrmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.hrmanager.hrmanager.entity.EmployeeEntity;
import uz.hrmanager.hrmanager.entity.LeaveBalanceEntity;
import uz.hrmanager.hrmanager.enums.LeaveType;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveBalanceRepository extends CrudRepository<LeaveBalanceEntity, Long> {

    // Xodim ID si va ta'til turi bo'yicha balansni topish
    Optional<LeaveBalanceEntity> findByEmployeeIdAndLeaveType(Integer employeeId, LeaveType leaveType);

    // Xodim ID si bo'yicha barcha balanslarni topish (Balansni ko'rish uchun)
    List<LeaveBalanceEntity> findAllByEmployeeId(Integer employeeId);
}
