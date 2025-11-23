package uz.hrmanager.hrmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import uz.hrmanager.hrmanager.entity.AttendanceEntity;
import uz.hrmanager.hrmanager.enums.AttendanceStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends CrudRepository<AttendanceEntity, Long> {

    // kunlik yozuvni topish (Tekshirish/Yangilash uchun)
    Optional<AttendanceEntity> findByEmployeeIdAndWorkDate(Integer employeeId, LocalDate workDate);

    // ma'lum davr uchun barcha xodimlar yozuvlarini olish (Hisobot uchun)
    List<AttendanceEntity> findAllByWorkDateBetween(LocalDate startDate, LocalDate endDate);

    // ma'lum davrda kechikkan yoki kelmaganlarni olish
    List<AttendanceEntity> findAllByWorkDateBetweenAndStatusIn(LocalDate startDate, LocalDate endDate, List<AttendanceStatus> statuses);
}