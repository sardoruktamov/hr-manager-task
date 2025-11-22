package uz.hrmanager.hrmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.hrmanager.hrmanager.enums.LeaveType;

@Getter
@Setter
@Entity
@Table(name = "leave_balance", //ikki marta bir xil turdagi tatil berilganda DB da xatolikni oldini olish uchun
        uniqueConstraints = {@UniqueConstraint(
                        name = "UK_employee_leave_type",
                        columnNames = {"employee_id", "leave_type"})})
public class LeaveBalanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    // Qaysi ta'til turi bo'yicha balans
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveType leaveType;

    // Yillik beriladigan tatil kunlari (masalan, 29 kalendar kuni bo'lishi mumkin)
    private Integer totalDays;

    // Ishlatilgan tatil kunlar
    private Integer usedDays = 0; // Boshida 0

    // Qolgan kunlar (totalDays - usedDays) - Bu qismni DBda saqlamay,
    // Getter usulida hisoblash yaxshiroq

    // Qolgan kunlarni hisoblash
    public Integer getRemainingDays() {
        return totalDays - usedDays;
    }

    // Har bir xodimda har bir turdan faqat 1 ta yozuv bo'lishini ta'minlash uchun
    // DB darajasida cheklov qo'yish kerak
}
