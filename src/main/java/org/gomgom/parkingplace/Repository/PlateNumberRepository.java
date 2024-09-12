package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.Entity.PlateNumber;
import org.gomgom.parkingplace.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlateNumberRepository extends JpaRepository<PlateNumber, Long> {

    // plate_number_id로 PlateNumber 엔티티 조회 후 CarType 반환
    @Query("SELECT p.carType FROM PlateNumber p WHERE p.plateNumber = :plateNumber")
    CarType findCarTypeByPlateNumberId(@Param("plateNumber") String plateNumber);


    @Query("SELECT pn FROM PlateNumber pn WHERE pn.user.id = (SELECT u.id FROM User u WHERE u.email = :email)")
    List<PlateNumber> getPlateNumbersByUserEmail(String email);

    /**
     * 작성자: 오지수
     * 2024.09.10 : User가 등록한 차량 찾기
     * @param user
     * @return List로 챠량 목록 반환
     */
    List<PlateNumber> findByUser(User user);

    /**
     * 작성자: 오지수
     * 2024.09.10 : User가 입력된 plateNumber 차량을 가지고 있는지 여부
     * @param plateNumber
     * @param user
     * @return true or false
     */
    boolean existsByPlateNumberAndUser(String plateNumber, User user);
}
