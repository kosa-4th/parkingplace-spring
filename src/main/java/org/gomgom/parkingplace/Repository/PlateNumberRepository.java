package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.Entity.PlateNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlateNumberRepository extends JpaRepository<PlateNumber, Long> {

    // plate_number_id로 PlateNumber 엔티티 조회 후 CarType 반환
    @Query("SELECT p.carType FROM PlateNumber p WHERE p.plateNumber = :plateNumber")
    CarType findCarTypeByPlateNumberId(@Param("plateNumber") String plateNumber);

}
