package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.Entity.ParkingSpace;
import org.gomgom.parkingplace.enums.CarTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Author : 김경민
 * @date : 2024-09-04
 *
 * */
@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    // 주차장과 차종을 기준으로 주차 공간 조회
    @Query("SELECT ps FROM ParkingSpace ps WHERE ps.parkingLot.id = :parkingLotId AND ps.carType.id = :carTypeId")
    Optional<ParkingSpace> findByParkingLotAndCarType(@Param("parkingLotId") Long parkingLotId, @Param("carTypeId") Long carTypeId);}
