package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.ParkingLotAndCarInfoDto;
import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.Repository.CarTypeRepository;
import org.gomgom.parkingplace.Repository.PlateNumberRepository;
import org.gomgom.parkingplace.Service.reservation.ParkingSeatSearchServiceImpl;
import org.gomgom.parkingplace.Service.reservation.ReservationServiceImpl;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.gomgom.parkingplace.Dto.ReservationDto.*;

@RestController
@RequestMapping("/api/parkingLots/{parkingLotId}/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ParkingSeatSearchServiceImpl parkingSeatSearchService;
    private final CarTypeRepository carTypeRepository;
    private final PlateNumberRepository plateNumberRepository;
    private final ReservationServiceImpl reservationService;

    /**
     * @Author 김경민
     * @DATE 2024.09.07 -> 컨트롤단 설정
     * @DATE 2024.09.09 -> VUE에 맞는 DTO 변경
     * <p>
     * 주차 조회 및 요금 조회 컨트롤단.
     */
    @GetMapping("/parkingCheck")
    public ResponseEntity<ReservationAvailableResponseDto> checkParkingSpaceSealAndTotalFee(
           @PathVariable Long parkingLotId,
            @RequestParam String plateNumber,
            @RequestParam String startTimeStr,
            @RequestParam String endTimeStr,
            @RequestParam boolean wash,
            @RequestParam boolean maintenance) {

        // 문자열로 받은 시작/종료 시간을 LocalDateTime으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);


        CarType carType = plateNumberRepository.findCarTypeByPlateNumberId(plateNumber);
        Optional<CarType> optionalCarType = carTypeRepository.findById(carType.getId());
        Bool washService = Bool.N;
        Bool maintenanceService = Bool.N;
        if(wash == true){
            washService = Bool.Y;
        }
        if(maintenance == true){
            maintenanceService = Bool.Y;
        }
        if (optionalCarType.isPresent()) {
            carType = optionalCarType.get();
        } else {
            throw new IllegalArgumentException("Invalid Car Type ID");
        }

        RequestAvailableDto requestAvailableDto = new RequestAvailableDto(parkingLotId, carType, startTime, endTime, washService, maintenanceService);

        ReservationAvailableResponseDto response = parkingSeatSearchService.isParkingSpaceAvailable(requestAvailableDto);
        return ResponseEntity.ok(response);
    }

    /**
     * @Author 김경민
     * @Date 2024.09.07
     * 주차자리 예약하기
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/protected")
    public ResponseEntity<ResponseReservationDto> createReservation(
            @PathVariable("parkingLotId") Long parkingLotId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody RequestReservationDto requestReservationDto) {


        Reservation reservation = reservationService.createReservation(parkingLotId, userDetails.getUser().getEmail(), requestReservationDto);

        ResponseReservationDto responseReservationDto = new ResponseReservationDto(reservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseReservationDto);
    }

    /*
    *@Author 김경민
    * @Date 2024.09.08
    * 예약 페이지 접근
    * */
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/protected")
    public ResponseEntity<?> getParkingLotReservation(
            @PathVariable("parkingLotId") Long parkingLotId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            String userEmail = userDetails.getUser().getEmail();
            // 주차장 및 사용자 차량 정보 가져오기
            ParkingLotAndCarInfoDto.ParkingLotAndCarInfoResponseDto reservationInfo = reservationService.getParkingLotReservation(parkingLotId, userEmail);
            return ResponseEntity.ok(reservationInfo);
        } catch (IllegalArgumentException e) {
            // 주차장 정보가 없는 경우, 오류 메시지와 함께 403 FORBIDDEN 상태 반환
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 불가한 페이지입니다.");
        }
    }

}
