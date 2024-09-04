package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author : 김경민
 * @date : 2024-09-04
 *
 * */
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {

    @Query("SELECT ps FROM ParkingSpace ps LEFT JOIN Reservation r " +
            "ON ps.id = r.parkingSpace.id " +
            "AND r.startTime < :endTime AND r.endTime > :startTime " +
            "WHERE ps.parkingLot.id = :parkingLotId AND r.id IS NULL")
    List<ParkingSpace> findAvailableSpaces(@Param("parkingLotId") Long parkingLotId,
                                           @Param("startTime") LocalDateTime startTime,
                                           @Param("endTime") LocalDateTime endTime);
}
