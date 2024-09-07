package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    //userID값을 통한 예약 내회 조회
    List<Reservation> findByUserId(Long userId);

    //예약번호 UUID가 존재하는지 확인
    boolean existsByReservationUuid(String uuid);

    @Query(value = "SELECT p.car_type_id, " +
            "(p.available_space_num - " +
            "(SELECT COUNT(*) FROM TBL_RESERVATION r " +
            "JOIN tbl_plate_number pl ON pl.plate_number = r.plate_number " +
            "WHERE r.parking_lot_id = p.parking_lot_id " +
            "AND pl.car_type_id = p.car_type_id " +
            "AND (r.start_time BETWEEN :startDate AND :endDate " +
            "OR r.end_time BETWEEN :startDate AND :endDate)) " +
            ") AS available_spaces " +
            "FROM TBL_PARKING_SPACE p " +
            "JOIN TBL_PARKING_LOT l ON p.parking_lot_id = l.parking_lot_id " +
            "WHERE l.parking_lot_id = :parkingLotId",
            nativeQuery = true)
    List<Object[]> findAvailableSpaces(@Param("parkingLotId") Long parkingLotId,
                                       @Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);

    
}
