package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.gomgom.parkingplace.Service.parkingLot.ParkingLotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * 생성 일자: 2024.09.05
 * 설명 : 주차장 관련 api 컨트롤러
 * ---------------------
 * 2024.09.05 양건모 | getParkingLots(): 특정 위도 경도 범위 내의 주차장 목록 조회
 *
 * */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parkinglots")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    //특정 위도 경도 범위 내의 주차장 목록 조회
    @GetMapping
    public ParkingLotDto.ParkingLotMarkersDto getParkingLots(
            @ModelAttribute ParkingLotDto.ParkingLotListRequestDto request) {
        return parkingLotService.getParkingLots(request);
    }

    /*
    작성자: 오지수
    주차장 정보 가져오기
     */
    @GetMapping("/{parkinglotId}")
    public ResponseEntity<ParkingLotDto.ParkingLotDetailResponseDto> getParkingLot(@PathVariable("parkinglotId") Long parkinglotId) {
        return ResponseEntity.ok(parkingLotService.getParkingLotDetail(parkinglotId));
    }

}
