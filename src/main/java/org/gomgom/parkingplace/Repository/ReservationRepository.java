package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * @DATE 2024.09.30
     * 입차예정, 출차예정, 예약대기 건수
     */
    //예약수락해야할 차량
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.parkingLot.id = :parkingLotId " +
            "AND r.reservationConfirmed = 'C' AND r.startTime > :currentTime")
    Long countPendingReservations(@Param("parkingLotId") Long parkingLotId, @Param("currentTime") LocalDateTime currentTime);

    //입차예정
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.parkingLot.id = :parkingLotId " +
            "AND r.reservationConfirmed = 'Y' " +
            "AND r.startTime > :currentTime " +  // 입차 시간이 현재 시간 이후
            "AND DATE(r.startTime) = CURRENT_DATE")
    Long countCheckInReservations(@Param("parkingLotId") Long parkingLotId, @Param("currentTime") LocalDateTime currentTime);

    //출차예정
    @Query("SELECT count(r)FROM Reservation r WHERE r.parkingLot.id = :parkingLotId " +
            "AND r.reservationConfirmed = 'Y' AND r.startTime <= :currentTime AND r.endTime > :currentTime")
    Long countCheckOutReservations(@Param("parkingLotId") Long parkingLotId, @Param("currentTime") LocalDateTime currentTime);

    /**
     * @Date 2024.09.28 토요일
     * 지난 주 이번 주 예약 건수
     */
    @Query("SELECT r FROM Reservation r WHERE r.parkingLot.id = :parkingLotId AND r.reservationConfirmed = 'Y' " +
            "AND DATE(r.startTime) >= :startDate " +
            "AND DATE(r.startTime) <= :endDate ")
    List<Reservation> findReservationsByDateRange(
            @Param("parkingLotId") Long parkingLotId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * @Date 2024.09.20
     * 입차예정 출차예정 출차완료에 대한 실시간 데이터
     */
    @Query("SELECT r FROM Reservation r " +
            "INNER JOIN r.parkingLot p " +
            "INNER JOIN r.user u " +
            "WHERE p.id = :parkingLotId " +
            "AND DATE(r.startTime) = DATE(:now) " +  // 날짜가 오늘인 데이터
            "AND r.startTime > :now " +
            "AND r.reservationConfirmed = 'Y' " +  // 현재 시간 이후인 데이터
            "ORDER BY r.startTime ASC")
    Page<Reservation> findTodayUpcomingEntries(
            @Param("parkingLotId") Long parkingLotId,
            @Param("now") LocalDateTime now,
            Pageable pageable);

    @Query("SELECT r FROM Reservation r " +
            "INNER JOIN r.parkingLot p " +
            "INNER JOIN r.user u " +
            "WHERE p.id = :parkingLotId " +
            "AND DATE(r.startTime) = DATE(:now) " +
            "AND r.startTime <= :now " +
            "AND r.endTime > :now " +
            "AND r.reservationConfirmed = 'Y' " +  // 현재 시간 이후인 데이터
            "ORDER BY r.endTime ASC")
    Page<Reservation> findTodayPendingExits(
            @Param("parkingLotId") Long parkingLotId,
            @Param("now") LocalDateTime now,
            Pageable pageable);

    @Query("SELECT r FROM Reservation r " +
            "INNER JOIN r.parkingLot p " +
            "INNER JOIN r.user u " +
            "WHERE p.id = :parkingLotId " +
            "AND DATE(r.endTime) = DATE(:now) " +  // 오늘 날짜의 데이터
            "AND r.endTime < :now " +  // 현재 시간보다 이전인 데이터
            "AND r.reservationConfirmed = 'Y' " +  // 현재 시간 이후인 데이터
            "ORDER BY r.endTime ASC")
    Page<Reservation> findTodayCompletedExits(
            @Param("parkingLotId") Long parkingLotId,
            @Param("now") LocalDateTime now,
            Pageable pageable);

    /**
     * @Date 2024.09.19
     * 검색 조건에 맞는 데이터 가져오기
     */
    @Query("SELECT r FROM Reservation r " +
            "INNER JOIN r.parkingLot p " +
            "INNER JOIN r.user u " +
            "WHERE p.id = :parkingLotId " +
            "AND r.reservationConfirmed = :reservationConfirmed " +
            "AND r.startTime BETWEEN :startTime AND :endTime " +
            "order by r.startTime asc")
    Page<Reservation> findReservationsByParkingLotAndConfirmedAndTimeRange(
            @Param("parkingLotId") Long parkingLotId,
            @Param("reservationConfirmed") Bool reservationConfirmed,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            Pageable pageable);

    @Query("SELECT r FROM Reservation r " +
            "INNER JOIN r.parkingLot p " +
            "INNER JOIN r.user u " +
            "WHERE p.id = :parkingLotId " +
            "AND r.reservationConfirmed = :reservationConfirmed " +
            "order by r.startTime asc")
    Page<Reservation> findReservationsByParkingLotAndConfirmed(
            @Param("parkingLotId") Long parkingLotId,
            @Param("reservationConfirmed") Bool reservationConfirmed,
            Pageable pageable);

    /**
     * @Date 2024.09.17
     * 예약 데이터 가져오기
     */
    @Query("SELECT r FROM Reservation r JOIN r.parkingLot tpl WHERE r.id = :reservationId AND r.user.id = :userId")
    Optional<Reservation> findReservationByIdAndUserId(@Param("reservationId") Long reservationId, @Param("userId") Long userId);

    /**
     * @Date 2024.09.14
     * 예약의 ConfirmedBy 상태 확인
     */
    @Query("SELECT r.reservationConfirmed FROM Reservation r WHERE r.id = :reservationId")
    Bool findReservationConfirmedByReservationId(@Param("reservationId") Long reservationId);


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
    @Query("UPDATE Reservation r SET r.reservationConfirmed = :status, r.updatedAt = CURRENT_TIMESTAMP WHERE r.id = :reservationId")
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
     *
     * @param user
     * @param startTime
     * @param endTime
     * @param pageable
     * @return
     */
    Page<Reservation> findByUserAndStartTimeGreaterThanEqualAndEndTimeLessThan(User user, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
}