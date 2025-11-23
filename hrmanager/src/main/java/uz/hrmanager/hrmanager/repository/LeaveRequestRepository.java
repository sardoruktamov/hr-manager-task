package uz.hrmanager.hrmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.hrmanager.hrmanager.entity.LeaveRequestEntity;
import uz.hrmanager.hrmanager.enums.LeaveStatus;
import java.util.List;
import java.util.Optional;
@Repository
public interface LeaveRequestRepository extends CrudRepository<LeaveRequestEntity, Integer> {

    // Xodim ID si bo'yicha barcha arizalarni topish
    List<LeaveRequestEntity> findAllByEmployeeId(Integer employeeId);

    // Manager ID si bo'yicha JARAYONDA  turgan arizalarni topish (Tasdiqlash uchun)
    // JARAYONDA = LeaveStatus.JARAYONDA
    List<LeaveRequestEntity> findAllByManagerIdAndStatus(Integer managerId, LeaveStatus status);

    // Xodim ID si va Status bo'yicha arizalarni topish
    List<LeaveRequestEntity> findAllByEmployeeIdAndStatus(Integer employeeId, LeaveStatus status);
}
