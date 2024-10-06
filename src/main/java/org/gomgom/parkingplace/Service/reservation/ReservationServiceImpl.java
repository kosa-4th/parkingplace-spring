package org.gomgom.parkingplace.Service.reservation;


import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.ReservationDto;
import org.gomgom.parkingplace.Dto.ReservationDto.RequestOwnerReservationDto;
import org.gomgom.parkingplace.Dto.ReservationDto.ResponseOwnerReservationDto;
import org.gomgom.parkingplace.Entity.*;
import org.gomgom.parkingplace.Repository.*;
import org.gomgom.parkingplace.Service.notification.NotificationService;
import org.gomgom.parkingplace.enums.Bool;
import org.gomgom.parkingplace.util.CustomUUIDGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.gomgom.parkingplace.Dto.ParkingLotAndCarInfoDto.*;
import static org.gomgom.parkingplace.Dto.ReservationDto.RequestReservationDto;
import static org.gomgom.parkingplace.Dto.ReservationDto.ResponseOwnerReservationStatusDto;


/**
 * ReservationServiceImpl.java
 * <p>
 * 예약 저장하는 로직
 *
 * @author 김경민
 * @Date 2024-09-07
 */
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CustomUUIDGenerator customUUIDGenerator;
    private final PlateNumberRepository plateNumberRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final NotificationService notificationService;

    /**
     * @Date 2024.10.04
     * 스케쥴러로 예약은 했으나,
     * 매 5분마다 예약 상태가 'C'인 예약들을 확인하고,
     * 시작 시간을 현재 시간보다 5분 전으로 설정하고 상태를 'D'로 변경합니다.
     */
    @Override
    @Scheduled(fixedDelay = 300000)  // 매 분마다 실행해야하나, aws와 서버 과부하를 방지하기 위해 5분
    @Transactional
    public void updateReservationsOverTime() {
        LocalDateTime now = LocalDateTime.now();
        List<Reservation> reservations = reservationRepository.findConfirmedReservations(Bool.C, now);

        for (Reservation reservation : reservations) {
            reservation.setStartTime(now);  // 시작 시간을 현재 시간보다 5분 전으로 설정
            reservation.setReservationConfirmed(Bool.D);  // 상태를 'D'로 변경
            reservationRepository.saveAndFlush(reservation);  // 변경 사항 저장 즉시 반영

            /***사업자라면 결제 취소 넣어야하지만, 결제취소는 당일 11시에 취소됨으로 별의미가 없음*/

            // 알림 메시지 생성
            String notificationMessage = "'" + reservation.getLotName() + "'에 대한 예약이 자동 취소되었습니다.";
            notificationService.createNotification(reservation.getUser().getId(), notificationMessage, "/my/reservations/" + reservation.getId());

            System.out.println("######++예약취소: " + notificationMessage);
        }
    }


    /**
     * @DATE 2024.09.20
     * 오늘의 예약 조회
     */
    @Override
    @Transactional
    public Page<ResponseOwnerReservationStatusDto> getTodayUpcomingEntries(Long parkingLotId, LocalDateTime now, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findTodayUpcomingEntries(parkingLotId, now, pageable);

        return reservations.map(reservation -> new ReservationDto.ResponseOwnerReservationStatusDto(
                reservation.getId(),
                reservation.getUser().getName(),
                reservation.getUser().getEmail(),
                reservation.getPlateNumber(),
                reservation.getParkingSpace().getSpaceName(),
                reservation.getReservationUuid(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getWash(),
                reservation.getMaintenance()
        ));
    }

    @Transactional

    public Page<ResponseOwnerReservationStatusDto> getTodayPendingExits(Long parkingLotId, LocalDateTime now, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findTodayPendingExits(parkingLotId, now, pageable);
        return reservations.map(reservation -> new ReservationDto.ResponseOwnerReservationStatusDto(
                reservation.getId(),
                reservation.getUser().getName(),
                reservation.getUser().getEmail(),
                reservation.getPlateNumber(),
                reservation.getParkingSpace().getSpaceName(),
                reservation.getReservationUuid(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getWash(),
                reservation.getMaintenance()
        ));
    }

    @Transactional

    public Page<ResponseOwnerReservationStatusDto> getTodayCompletedExits(Long parkingLotId, LocalDateTime now, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findTodayCompletedExits(parkingLotId, now, pageable);

        return reservations.map(reservation -> new ReservationDto.ResponseOwnerReservationStatusDto(
                reservation.getId(),
                reservation.getUser().getName(),
                reservation.getUser().getEmail(),
                reservation.getPlateNumber(),
                reservation.getParkingSpace().getSpaceName(),
                reservation.getReservationUuid(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getWash(),
                reservation.getMaintenance()
        ));
    }

    /**
     * @Date 2024.09.20
     * 입차 예정, 출차 예정, 출차 완료 서비스처리
     * ------------------------------------
     * 2024.09.25 양건모 | 알림 생성 로직 추가
     */

    //예약 허가 삭제.
    @Override
    @Transactional
    public int updateReservationStatus(Long reservationId, Bool reservationConfirmed) {
        Reservation reservation = reservationRepository.findReservationById(reservationId).orElseThrow();
        String notificationMessage;
        String parkingLotName = reservation.getLotName();

        if (reservationConfirmed == Bool.Y) {
            notificationMessage = "'" + parkingLotName + "'에 대한 예약이 확정되었습니다.";
            int resultValue = reservationRepository.updateReservationStatus(reservationId, Bool.Y);
            notificationService.createNotification(reservation.getUser().getId(), notificationMessage, "/my/reservations/" + reservationId);
            return resultValue;
        } else if (reservationConfirmed == Bool.D) {
            notificationMessage = "'" + parkingLotName + "'에 대한 예약이 거절되었습니다.";
            notificationService.createNotification(reservation.getUser().getId(), notificationMessage, "/my/reservations/" + reservationId);
            return reservationRepository.updateReservationStatus(reservationId, Bool.D);
        }

        return 0;

    }

    /**
     * @Author 김경민
     * @Date 2024.09.19 -> 사용자 페이지 예약 괸련 정보 조회
     */
    @Transactional //사용한 이유 지연 된 로된 필드를 가져올 수 있음
    public Page<ResponseOwnerReservationDto> getOwnerReservations(
            RequestOwnerReservationDto requestOwnerReservationDto, Pageable pageable) {

        Page<Reservation> reservations;

        Long parkingLotId = requestOwnerReservationDto.getParkingLotId();
        LocalDateTime startTime = requestOwnerReservationDto.getStartTime();
        LocalDateTime endTime = requestOwnerReservationDto.getEndTime();
        Bool reservationConfirmed = requestOwnerReservationDto.getReservationConfirmed();
        System.out.println(parkingLotId + "  " + reservationConfirmed + "#########");
        if (startTime != null && endTime != null) {
            reservations = reservationRepository.findReservationsByParkingLotAndConfirmedAndTimeRange(parkingLotId, reservationConfirmed, startTime, endTime, pageable);
        } else {
            reservations = reservationRepository.findReservationsByParkingLotAndConfirmed(parkingLotId, reservationConfirmed, pageable);
        }
        return reservations.map(reservation -> new ResponseOwnerReservationDto(
                reservation.getId(),
                reservation.getUser() != null ? reservation.getUser().getName() : "",
                reservation.getUser() != null ? reservation.getUser().getEmail() : "",
                reservation.getPlateNumber(),
                reservation.getParkingSpace() != null ? reservation.getParkingSpace().getSpaceName() : "",
                reservation.getReservationUuid(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getReservationConfirmed(),
                reservation.getWash(),
                reservation.getMaintenance()
        ));
    }

    /**
     * @Author 김경민
     * @Date 2024.09.14 -> 예약 취소 서비스메소드 군현
     * @Date 2024.09.18 -> 소스 check 값 수정
     */
    public int cancelReservation(Long ReservationId) {
        Reservation reservation = reservationRepository.findById(ReservationId).orElseThrow();
        Bool check = reservationRepository.findReservationConfirmedByReservationId(ReservationId);
        if (check == Bool.N || check == Bool.Y || check == Bool.C) {
            String notificationMessage = "'" + reservation.getLotName() + "'에 대한 예약이 취소되었습니다.";
            notificationService.createNotification(reservation.getUser().getId(), notificationMessage, "/my/reservations/" + ReservationId);
            return reservationRepository.updateReservationStatus(ReservationId, Bool.D);
        }
        return 0;
    }

    /**
     * @Author 김경민
     * @Date 2024.09.07
     * <p>
     * 예약 저장하는 로직
     */
    @Transactional
    public Reservation createReservation(Long parkingLotId, String userEmail, RequestReservationDto requestReservationDto) {
        Reservation reservation = new Reservation();


        String parkingLotName = parkingLotRepository.findByParkingLotName(parkingLotId);


        String reservationUuid = customUUIDGenerator.generateUniqueUUID();


        ParkingSpace parkingSpace = parkingSpaceRepository.findById(requestReservationDto.getParkingSpaceId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Parking Space ID"));


        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Email 존재 안함"));


        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new IllegalArgumentException("parkingLot 존재 안함."));


        String plateNumber = requestReservationDto.getPlateNumber();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(requestReservationDto.getStartTime(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(requestReservationDto.getEndTime(), formatter);
        Bool wash = requestReservationDto.getWashService();
        Bool maintenance = requestReservationDto.getMaintenanceService();
        int totalPrice = requestReservationDto.getTotalPrice();

        reservation.setLotName(parkingLotName);
        reservation.setReservationUuid(reservationUuid);
        reservation.setPlateNumber(plateNumber);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setWash(wash);
        reservation.setMaintenance(maintenance);
        reservation.setTotalPrice(totalPrice);
        reservation.setUser(user);
        reservation.setParkingLot(parkingLot);
        reservation.setParkingSpace(parkingSpace);
        reservation.setReservationConfirmed(Bool.N);
        return reservationRepository.save(reservation);

    }

    /**
     * @Author 김경민
     * @Date 2024.09.07 주차장예약페이지 데이터 조회
     */
    public ParkingLotAndCarInfoResponseDto getParkingLotReservation(Long parkingLotId, String userEmail) {
        // 주차장 정보 가져오기
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주차장을 찾을 수 없습니다."));

        // 주차장에 연결된 사용자 정보가 null이거나 ID가 null일 경우 예외 처리
        if (parkingLot.getUser() == null || parkingLot.getUser().getId() == null) {
            throw new IllegalStateException("해당 주차장의 사용자 정보가 유효하지 않습니다. 페이지 접근이 불가합니다.");
        }

        // 주차장 정보 DTO로 변환
        ParkingLotReservationResponseDto parkingLotInfo = new ParkingLotReservationResponseDto(
                parkingLot.getName(),
                parkingLot.getWash(),
                parkingLot.getMaintenance(),
                parkingLot.getWeekdaysOpenTime(),
                parkingLot.getWeekendOpenTime(),
                parkingLot.getWeekdaysCloseTime(),
                parkingLot.getWeekendCloseTime(),
                parkingLot.getUser().getId()
        );

        // 사용자 이메일로 차량 정보 가져오기
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        // 사용자 이메일로 차량 정보 가져오기
        List<PlateNumberDto> userCarDtos = new ArrayList<>();
        for (PlateNumber plateNumber : plateNumberRepository.getPlateNumbersByUserEmail(userEmail)) {
            PlateNumberDto plateNumberDto = new PlateNumberDto(
                    plateNumber.getPlateNumber(),
                    plateNumber.getCarType().getId()
            );
            userCarDtos.add(plateNumberDto);
        }

        // DTO 반환
        return new ParkingLotAndCarInfoResponseDto(parkingLotInfo, userCarDtos);
    }

    /**
     * @Author 김경민
     * @Date 2024.09.11
     * 예약 생성 후 5분 후까지 결제가 되지 않을 시 예약 삭제
     */
    // 1분마다 실행
    @Scheduled(fixedRate = 60000) // 60초 = 1분
    @Transactional
    public void checkUnpaidReservations() {
        // 5분 이전에 생성된 결제되지 않은 예약(N 상태)를 조회
        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(5);
        List<Reservation> unpaidReservations = reservationRepository.findByReservationConfirmedAndCreatedAtBefore(Bool.N, cutoffTime);

        // 상태를 'D'로 변경
        unpaidReservations.forEach(reservation -> {
            reservationRepository.updateExpiredReservations(Bool.D, Bool.N, cutoffTime);
            System.out.println("미결제된 예약이 'D'로 변경되었습니다. 예약 ID: " + reservation.getId());
        });
    }
}
