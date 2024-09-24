package org.gomgom.parkingplace.Service.reservation;
import static org.gomgom.parkingplace.Dto.ReservationDto.*;

import static org.gomgom.parkingplace.Dto.ParkingLotAndCarInfoDto.*;
import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * ReservationService.java
 *
 * @author 김경민
 * @date 2024-09-03
 */
public interface ReservationService {
    /**
     * @Date 2024.09.20
     * 입차 예정 / 출차 예정 / 출차 완료 서비스단
     */
    Page<ResponseOwnerReservationStatusDto> getTodayUpcomingEntries(Long parkingLotId, LocalDateTime now, Pageable pageable);
    Page<ResponseOwnerReservationStatusDto> getTodayPendingExits(Long parkingLotId, LocalDateTime now, Pageable pageable);
    Page<ResponseOwnerReservationStatusDto> getTodayCompletedExits(Long parkingLotId, LocalDateTime now, Pageable pageable);

    /**
     * @Author 김경민
     * @Date 2024.09.19 -> 예약 서비스를 예약 대기 후 예약 수락
     */
    int updateReservationStatus(Long reservationId, Bool reservationConfirmed);

    /**
     * @Author 김경민
     * @Date 2024.09.19 -> 사용자 페이지 예약 관련 정보 조회
     */
    Page<ResponseOwnerReservationDto> getOwnerReservations(
            RequestOwnerReservationDto requestOwnerReservationDto, Pageable pageable);

    /**
     * @Author 김경민
     * @Date 2024.09.14 -> 예약 취소 서비스 메소드
     * @Date 2024.09.18 -> 소스 check 값 수정
     */
    int cancelReservation(Long ReservationId);

    /**
     * @Author 김경민
     * @Date 2024.09.07 -> 예약 저장하는 로직
     */
    Reservation createReservation(Long parkingLotId, String userEmail, RequestReservationDto requestReservationDto);

    /**
     * @Author 김경민
     * @Date 2024.09.07 -> 주차장 예약 페이지 데이터 조회
     */
    ParkingLotAndCarInfoResponseDto getParkingLotReservation(Long parkingLotId, String userEmail);

    /**
     * @Author 김경민
     * @Date 2024.09.11 -> 예약 생성 후 5분 후까지 결제가 되지 않을 시 예약 삭제
     */
    void checkUnpaidReservations();


}