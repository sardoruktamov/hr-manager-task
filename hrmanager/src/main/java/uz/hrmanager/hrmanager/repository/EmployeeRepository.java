package uz.hrmanager.hrmanager.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.hrmanager.hrmanager.entity.EmployeeEntity;
import uz.hrmanager.hrmanager.enums.Position;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Integer> {
    Optional<EmployeeEntity> findByPhoneAndActiveTrue(String phone);

    // Boshqa ID ga tegishli bo'lgan, ammo berilgan phone raqami bilan ishlaydigan  faol xodimni topish
    Optional<EmployeeEntity> findByPhoneAndIdNotAndActiveTrue(String phone, Integer id);
    @Query(value = "SELECT * FROM employee e WHERE e.active = true", nativeQuery = true)
    List<EmployeeEntity> findAllByActiveTrue();

    Optional<EmployeeEntity> findByPhoneAndIdNot(String phone, Integer id);

    // checking phone
    boolean existsByPhoneAndActiveTrue(String phone);

    // checking Position (RAXBAR)
    boolean existsByPositionAndActiveTrue(Position position);
    Optional<EmployeeEntity> findByIdAndActiveTrue(Integer id);
    // Ism yoki familya bo'yicha qidirish
    @Query(
            value = """
                    SELECT * FROM employee e
                    WHERE LOWER(e.first_name) LIKE LOWER(CONCAT('%', :word, '%'))
                       OR LOWER(e.last_name)  LIKE LOWER(CONCAT('%', :word, '%'))
                    """, nativeQuery = true
    )
    List<EmployeeEntity> searchAllByName(@Param("word") String word);


    // Lavozim bo'yicha filter
    @Query(value = "SELECT * FROM employee e WHERE e.position = :position", nativeQuery = true)
    List<EmployeeEntity> findByPosition(@Param("position") String position);


    // Bo'lim bo'yicha filter
    @Query(value = "SELECT * FROM employee e WHERE e.department = :department",nativeQuery = true)
    List<EmployeeEntity> findByDepartment(@Param("department") String department);


    // Active bo'yicha filter
    @Query(value = "SELECT * FROM employee e WHERE e.active = :active",nativeQuery = true)
    Optional<EmployeeEntity> findByActive(@Param("active") boolean active);

    // arizani Raxbar tomonidan tasdiqlashi uchun topish
     Optional<EmployeeEntity> findByPositionAndActiveTrue(Position raxbar);
}

