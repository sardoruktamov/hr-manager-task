package uz.hrmanager.hrmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.hrmanager.hrmanager.entity.EmployeeEntity;
import uz.hrmanager.hrmanager.entity.LeaveBalanceEntity;
import uz.hrmanager.hrmanager.enums.LeaveType;
import uz.hrmanager.hrmanager.exceptions.AppBadException;
import uz.hrmanager.hrmanager.repository.LeaveBalanceRepository;

import java.util.List;

@Service
public class LeaveBalanceService {

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    public void initializeLeaveBalance(EmployeeEntity newEmployee) {
        // Balans berilishi kerak bo'lgan ta'til turlarini Enum orqali aylanib chiqamiz
        for (LeaveType type : LeaveType.values()) {

            // Agar ta'til turi UZ_HISOBIDAN bo'lsa, balans berilmaydi
            if (type == LeaveType.UZ_HISOBIDAN) {
                continue;
            }

            LeaveBalanceEntity balance = new LeaveBalanceEntity();
            balance.setEmployee(newEmployee);
            balance.setLeaveType(type);

            // Har bir ta'til turiga mos keladigan boshlang'ich kunlar sonini belgilash
            switch (type) {
                case YILLIK:
                    balance.setTotalDays(27); // Masalan, 24 kun
                    break;
                case KASALLIK:
                    balance.setTotalDays(15); // Masalan, 15 kun
                    break;
                case MAXSUS:
                    balance.setTotalDays(7); // Masalan, 5 kun
                    break;
                default:
                    balance.setTotalDays(0); // Boshqa noma'lum turlar uchun 0
            }

            balance.setUsedDays(0); // Yangi xodim uchun ishlatilgan kunlar 0
            leaveBalanceRepository.save(balance);
        }
    }

    // --- 1. Balans yetarliligini tekshirish ---
    public boolean isBalanceSufficient(Integer employeeId, LeaveType leaveType, Integer requestedDays) {

        // Faqat haqiqiy ta'til turlari tekshiriladi
        if (leaveType == LeaveType.UZ_HISOBIDAN) {
            return true;
        }

        LeaveBalanceEntity balance = leaveBalanceRepository.findByEmployeeIdAndLeaveType(employeeId, leaveType)
                .orElseThrow(() -> new AppBadException(leaveType + " ta'til turi uchun balans topilmadi"));

        return balance.getRemainingDays() >= requestedDays;
    }

    // --- 2. Balansni yangilash (Tasdiqlashdan so'ng) ---
    public void updateBalanceOnApproval(Integer employeeId, LeaveType leaveType, Integer usedDays) {
        if (leaveType == LeaveType.UZ_HISOBIDAN) {
            return; // O'z hisobidan ta'til uchun balans yangilanmaydi
        }

        LeaveBalanceEntity balance = leaveBalanceRepository.findByEmployeeIdAndLeaveType(employeeId, leaveType)
                .orElseThrow(() -> new AppBadException(leaveType + " ta'til turi uchun balans topilmadi."));

//        if (balance.getRemainingDays() < usedDays) {
//            // Bu tekshiruv oldin (create) qilingan bo'lsa ham, yana bir marta tekshiruv yaxlitlikni oshiradi
//            throw new AppBadException("Balansni yangilashda xato: qolgan kunlar yetarli emas.");
//        }

        balance.setUsedDays(balance.getUsedDays() + usedDays);
        leaveBalanceRepository.save(balance);
    }




}
