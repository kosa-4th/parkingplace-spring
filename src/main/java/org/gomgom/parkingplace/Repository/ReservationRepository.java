package org.gomgom.parkingplace.Repository;

import jakarta.transaction.Transactional;
import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.gomgom.parkingplace.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    //userID값을 통한 예약 내회 조회
    List<Reservation> findByUserId(Long userId);

    //예약번호 UUID가 존재하는지 확인
    boolean existsByReservationUuid(String uuid);

    @Query("SELECT r FROM Reservation r WHERE r.id = :reservationId")
    Optional<Reservation> findReservationById(@Param("reservationId") Long reservationId);

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


    /**
     * @Author 김경민
     * @Date 2024.09.11
     */
    //예약 여부 수정
    @Modifying
    @Transactional
    @Query("UPDATE Reservation r SET r.reservationConfirmed = :status WHERE r.id = :reservationId")
    int updateReservationStatus(@Param("reservationId") Long reservationId, @Param("status") Bool status);


    /**
     * @Author 김경민
     * @Date 2024.09.11
     */
    //생성시간 기준 5분마다 삭제.
    @Modifying
    @Transactional
    @Query("UPDATE Reservation r SET r.reservationConfirmed = :status WHERE r.reservationConfirmed = :currentStatus AND r.createdAt < :time")
    int updateExpiredReservations(@Param("status") Bool status, @Param("currentStatus") Bool currentStatus, @Param("time") LocalDateTime time);

    /**
     * @Author 김경민
     * @Date 2024.09.11
     */
    //생성시간 기준 5분마다 N인거 찾음.
    @Query("SELECT r FROM Reservation r WHERE r.reservationConfirmed = :reservationConfirmed AND r.createdAt < :time")
    List<Reservation> findByReservationConfirmedAndCreatedAtBefore(@Param("reservationConfirmed") Bool reservationConfirmed, @Param("time") LocalDateTime time);

    /**
     * 작성자: 오지수
     * 2024.09.11 : 입력한 날짜 사이에 있는 예약 목록 반환
     * @param user
     * @param startTime
     * @param endTime
     * @param pageable
     * @return
     */
    Page<Reservation> findByUserAndStartTimeGreaterThanEqualAndEndTimeLessThan(User user, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
}