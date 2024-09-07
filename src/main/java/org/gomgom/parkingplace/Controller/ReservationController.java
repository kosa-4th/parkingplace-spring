package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.ReservationDto;
import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.Repository.CarTypeRepository;
import org.gomgom.parkingplace.Service.reservation.ParkingSeatSearchServiceImpl;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.gomgom.parkingplace.Dto.ReservationDto.RequestAvailableDto;
import static org.gomgom.parkingplace.Dto.ReservationDto.ReservationAvailableResponseDto;

@RestController
@RequestMapping("/api/parkingLots/{parkingLotId}/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ParkingSeatSearchServiceImpl parkingSeatSearchService;
    private final CarTypeRepository carTypeRepository;

    @GetMapping("/parkingCheck")
    public ResponseEntity<ReservationAvailableResponseDto> checkParkingSpaceSealAndTotalFee(
            @PathVariable Long parkingLotId,
            @RequestParam Long carTypeId,
            @RequestParam String startTimeStr,
            @RequestParam String endTimeStr,
            @RequestParam Bool washService,
            @RequestParam Bool maintenanceService) {

        // 문자열로 받은 시작/종료 시간을 LocalDateTime으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);
        Optional<CarType> optionalCarType = carTypeRepository.findById(carTypeId);

        CarType carType;

        if (optionalCarType.isPresent()) {
            carType = optionalCarType.get();
        } else {
            throw new IllegalArgumentException("Invalid Car Type ID");
        }

        RequestAvailableDto requestAvailableDto = new RequestAvailableDto(parkingLotId, carType, startTime, endTime, washService, maintenanceService);

        ReservationDto.ReservationAvailableResponseDto response = parkingSeatSearchService.isParkingSpaceAvailable(requestAvailableDto);
        return ResponseEntity.ok(response) ;
    }


}
