package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.Repository.CarTypeRepository;
import org.gomgom.parkingplace.Repository.PlateNumberRepository;
import org.gomgom.parkingplace.Service.reservation.ParkingSeatSearchServiceImpl;
import org.gomgom.parkingplace.Service.reservation.ReservationServiceImpl;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * @DATE 2024.09.07
     * <p>
     * 주차 조회 및 요금 조회 컨트롤단.
     */
    @GetMapping("/parkingCheck")
    public ResponseEntity<ReservationAvailableResponseDto> checkParkingSpaceSealAndTotalFee(
            @PathVariable Long parkingLotId,
            @RequestParam String plateNumber,
            @RequestParam String startTimeStr,
            @RequestParam String endTimeStr,
            @RequestParam Bool washService,
            @RequestParam Bool maintenanceService) {

        // 문자열로 받은 시작/종료 시간을 LocalDateTime으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);
        CarType carType = plateNumberRepository.findCarTypeByPlateNumberId(plateNumber);
        Optional<CarType> optionalCarType = carTypeRepository.findById(carType.getId());


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


    @PostMapping("/reservation")
    public ResponseEntity<ResponseReservationDto> createReservation(@RequestBody RequestReservationDto requestReservationDto) {

        Reservation reservation = reservationService.createReservation(requestReservationDto);

        ResponseReservationDto responseReservationDto = new ResponseReservationDto(reservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseReservationDto);
    }

}
