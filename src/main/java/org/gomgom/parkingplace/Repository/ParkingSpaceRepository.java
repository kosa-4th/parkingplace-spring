package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.Dto.ParkingSpaceDto;
import org.gomgom.parkingplace.Entity.ParkingLot;
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
    Optional<ParkingSpace> findByParkingLotAndCarType(@Param("parkingLotId") Long parkingLotId, @Param("carTypeId") Long carTypeId);

    @Query("SELECT ps FROM ParkingSpace ps LEFT JOIN Reservation r " +
            "ON ps.id = r.parkingSpace.id " +
            "AND r.startTime < :endTime AND r.endTime > :startTime " +
            "WHERE ps.parkingLot.id = :parkingLotId AND r.id IS NULL")
    List<ParkingSpace> findAvailableSpaces(@Param("parkingLotId") Long parkingLotId,
                                           @Param("startTime") LocalDateTime startTime,
                                           @Param("endTime") LocalDateTime endTime);

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.07
     * 설명 : 주차장의 id를 통해 해당 주차장의 주차 구역 목록을 제공
     * @param parkingLotId 주차장 id
     * @return List 형태로 주차가능차량, 평일가격, 주말가격, 평일최대가격, 주말최대가격
     *  ---------------------
     * 2024.09.07 양건모 | 기능 구현
     * */
    @Query("SELECT new org.gomgom.parkingplace.Dto.ParkingSpaceDto$ParkingSpacesPreviewDto(" +
            "ps.carType.carTypeEnum, ps.weekdaysPrice, ps.weekendPrice, ps.weekAllDayPrice, ps.weekendAllDayPrice) " +
            "FROM ParkingSpace ps " +
            "WHERE ps.parkingLot.id = :parkingLotId " +
            "ORDER BY ps.carType.id")
    List<ParkingSpaceDto.ParkingSpacesPreviewDto> getSpacesPreviewsByParkingLotId(Long parkingLotId);

}
