package uz.hrmanager.hrmanager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.hrmanager.hrmanager.dto.AttendanceCreateDTO;
import uz.hrmanager.hrmanager.dto.AttendanceReportDTO;
import uz.hrmanager.hrmanager.entity.AttendanceEntity;
import uz.hrmanager.hrmanager.entity.EmployeeEntity;
import uz.hrmanager.hrmanager.enums.AttendanceStatus;
import uz.hrmanager.hrmanager.exceptions.AppBadException;
import uz.hrmanager.hrmanager.repository.AttendanceRepository;
import uz.hrmanager.hrmanager.repository.EmployeeRepository;
import uz.hrmanager.hrmanager.service.mapper.AttendanceMapper;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AttendanceMapper attendanceMapper;


    // Majburiy ish vaqti
    private static final LocalTime REQUIRED_IN_TIME = LocalTime.of(9, 0); // =09:00
    //  statusni aniqlash
    public AttendanceStatus determineStatus(LocalTime inTime) { // "inTime": "09:30:00"
        // xodimni ishga kelgan yoki kelmaganligi aniqlash
        if (inTime == null) {
            return AttendanceStatus.ABSENT;
        }

        // 09:00 dan keyin kech qolib kelganini tekshiramiz
        // REQUIRED_IN_TIME=09:00:00, inTime=09:30:00 kabi tekshiryapmiz
        if (inTime.isAfter(REQUIRED_IN_TIME)) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ON_TIME;
    }

    //  Kunlik Kirish/Chiqishni Belgilash
    public AttendanceEntity hrSetAttendance(Integer employeeId, AttendanceCreateDTO dto) {

        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new AppBadException("Xodim topilmadi."));

        Optional<AttendanceEntity> optional = attendanceRepository.findByEmployeeIdAndWorkDate(employeeId, dto.getWorkDate());

        AttendanceEntity entity;

        if (optional.isPresent()) {

            entity = optional.get();
            log.info("employeni nomiiiiiiiiiiiii---{}", entity.getEmployee().getFirstName());
            AttendanceStatus currentStatus = entity.getStatus();

            if (currentStatus == AttendanceStatus.LATE || currentStatus == AttendanceStatus.ON_TIME) {
                log.info("------currentStatus == AttendanceStatus.LATE-------{}", entity.getEmployee().getFirstName());
                // Agar  InTime yoki OutTime  o'chirilsa (null qilsa), u holda status-ABSENT ga o'zgarishi kerak.
                if (dto.getInTime() == null && entity.getInTime() != null) {
                    log.info("-----dto.getInTime() == null----{}", entity.getEmployee().getFirstName());
                    // Agar avval inTime bor edi, endi HR uni o'chirsa, status ABSENT ga o'zgaradi.
                    entity.setStatus(AttendanceStatus.ABSENT);
                }
                throw new AppBadException("Kechikkan  vaqtni(" + entity.getInTime() + " sanasi)  o'chirilishi yoki statusini ON_TIME ga o'zgartirish taqiqlanadi.");
            }else {
                // Agar status ABSENT bo'lsa rusxat.
                entity.setStatus(determineStatus(dto.getInTime()));
            }

        } else {
            log.info("-----seeeeetttt----");
            entity = new AttendanceEntity();
            entity.setEmployee(employee);
            entity.setWorkDate(dto.getWorkDate());
            // bir kunda birinchi marta ishga kelganda statusni belgilaymiz.
            entity.setStatus(determineStatus(dto.getInTime()));
        }

        entity.setEmployee(employee);
        entity.setWorkDate(dto.getWorkDate());
        entity.setInTime(dto.getInTime());
        entity.setOutTime(dto.getOutTime());
        entity.setStatus(determineStatus(dto.getInTime()));
        entity.setComment(dto.getComment());

        return attendanceRepository.save(entity);
    }

    // Ishga Kelish (Check-In)
    public void checkIn(Integer employeeId) {

        // Server vaqtini olamiz
        LocalDate today = LocalDate.now();
        LocalTime checkInTime = LocalTime.now();

        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new AppBadException("Xodim topilmadi."));

        // Bugungi yozuvni tekshirish
        Optional<AttendanceEntity> optional = attendanceRepository.findByEmployeeIdAndWorkDate(employeeId, today);

        if (optional.isPresent()) {
            throw new AppBadException("Siz bugun allaqachon ishga kelganingizni belgilagansiz!");
        }

        // Yangi yozuv yaratish (Create)
        AttendanceEntity entity = new AttendanceEntity();
        entity.setEmployee(employee);
        entity.setWorkDate(today);
        entity.setInTime(checkInTime);

        // Statusni hisoblash (bu yerda faqat check-in vaqti bor)
        entity.setStatus(determineStatus(checkInTime));

        attendanceRepository.save(entity);
    }


    // Ishdan Ketish (Check-Out)
    public void checkOut(Integer employeeId) {

        LocalDate today = LocalDate.now();
        LocalTime checkOutTime = LocalTime.now();

        // Bugungi yozuvni topish shart
        AttendanceEntity entity = attendanceRepository.findByEmployeeIdAndWorkDate(employeeId, today)
                .orElseThrow(() -> new AppBadException("Ishdan ketish vaqtini belgilash uchun avval ishga kelish vaqtini (Check-In) belgilashingiz kerak."));

        if (entity.getOutTime() != null) {
            throw new AppBadException("Siz bugun ishdan ketishingizni allaqachon belgilagansiz!");
        }

        entity.setOutTime(checkOutTime);
        attendanceRepository.save(entity);
    }

    // Kechikishlar va Kelmaganlar Hisobotini olish
    public List<AttendanceReportDTO> getLateAndAbsenceReport(LocalDate startDate, LocalDate endDate) {

        List<AttendanceStatus> targetStatuses = Arrays.asList(AttendanceStatus.LATE, AttendanceStatus.ABSENT);

        List<AttendanceEntity> records = attendanceRepository.findAllByWorkDateBetweenAndStatusIn(startDate, endDate, targetStatuses);

        return records.stream()
                .map(record -> attendanceMapper.toReportDTO(record))
                .collect(Collectors.toList());
    }

    // ikkita sana oraligini hisobotini olish
    public List<AttendanceReportDTO> getWorkDateBetween(LocalDate startDate, LocalDate endDate){
        List<AttendanceEntity> recodrs = attendanceRepository.findAllByWorkDateBetween(startDate, endDate);
        return recodrs.stream()
                .map(recodr -> attendanceMapper.toReportDTO(recodr))
                .collect(Collectors.toList());
    }
}