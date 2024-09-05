package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.gomgom.parkingplace.Service.parkingLot.ParkingLotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//    @GetMapping
//    public List<ParkingLotDto.ParkingLotListResponseDto> getParkingLots(
//            @RequestBody ParkingLotDto.ParkingLotListRequestDto request) {
//        parkingLotService.getParkingLots();
//    }
}
