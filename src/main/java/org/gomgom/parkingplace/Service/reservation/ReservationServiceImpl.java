package org.gomgom.parkingplace.Service.reservation;


import lombok.RequiredArgsConstructor;
import static org.gomgom.parkingplace.Dto.ReservationDto.RequestReservationDto;

import static org.gomgom.parkingplace.Dto.ParkingLotAndCarInfoDto.PlateNumberDto;
import static org.gomgom.parkingplace.Dto.ParkingLotAndCarInfoDto.ParkingLotReservationResponseDto;
import static org.gomgom.parkingplace.Dto.ParkingLotAndCarInfoDto.ParkingLotAndCarInfoResponseDto;

import org.gomgom.parkingplace.Dto.ReservationDto;
import org.gomgom.parkingplace.Entity.*;
import org.gomgom.parkingplace.Repository.*;
import org.gomgom.parkingplace.enums.Bool;
import org.gomgom.parkingplace.util.CustomUUIDGenerator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * ReservationServiceImpl.java
 *
 * 예약 저장하는 로직
 *
 * @author 김경민
 * @date 2024-09-07
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

    public int cancelReservation(Long ReservationId){
        Bool check = reservationRepository.findReservationConfirmedByReservationId(ReservationId);
        if(check==Bool.N){
            return reservationRepository.updateReservationStatus(ReservationId, Bool.D);
        }
        return 0;
    }

    /**
     * @Author 김경민
     * @Date 2024.09.07
     *
     * 예약 저장하는 로직
     *
     * */
    @Transactional
    public Reservation createReservation(Long parkingLotId, String userEmail, RequestReservationDto requestReservationDto){
        Reservation reservation = new Reservation();


        String parkingLotName = parkingLotRepository.findByParkingLotName(parkingLotId);


        String reservationUuid = customUUIDGenerator.generateUniqueUUID();


        ParkingSpace parkingSpace = parkingSpaceRepository.findById(requestReservationDto.getParkingSpaceId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Parking Space ID"));


        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->new IllegalArgumentException("Email 존재 안함"));


        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                        .orElseThrow(()->new IllegalArgumentException("parkingLot 존재 안함."));


        String plateNumber = requestReservationDto.getPlateNumber();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(requestReservationDto.getStartTime(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(requestReservationDto.getEndTime(), formatter);
        Bool wash = requestReservationDto.getWashService();
        Bool maintenance =  requestReservationDto.getMaintenanceService();
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
     * */
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
     * */
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
