package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.gomgom.parkingplace.Service.parkingLot.ParkingLotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parkinglots")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.02
     * 설명 : 위도 경도 범위 내의 주차장들의 위도, 경도를 포함한 정보 제공
     * @param request 최소 위도, 최대 위도, 최소 경도, 최대 경도
     * @return List 형태로 id, 이름, 위도, 경도, 주소
     *  ---------------------
     * 2024.09.05 양건모 | 기능 구현
     * 2024.09.07 양건모 | 반환 DTO 이름 변경에 대한 적용
     * 2024.09.07 양건모 | 매핑 url api 문서에 맞게 변환
     * 2024.09.10 양건모 | api 문서 수저에 따라 매핑 url 변경
     * */
    @GetMapping
    public ResponseEntity<ParkingLotDto.ParkingLotMarkersResponseDto> getParkingLots(
            @ModelAttribute ParkingLotDto.ParkingLotListRequestDto request) {
        return ResponseEntity.ok(parkingLotService.getParkingLots(request));
    }

    /**
     * 작성자: 오지수
     * ? : 주차장 상세 페이지 정보 제공
     * @param parkinglotId
     * @return 주차장 이름, 유형, 주소, 전화번호, 운영시간, 가격, 사진
     */
    @GetMapping("/{parkinglotId}")
    public ResponseEntity<ParkingLotDto.ParkingLotDetailResponseDto> getParkingLot(@PathVariable("parkinglotId") Long parkinglotId) {
        return ResponseEntity.ok(parkingLotService.getParkingLotDetail(parkinglotId));
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.07
     * 설명 : 주차장의 간략한 정보 제공
     * @param parkingLotId 주차장 id
     * @return 주차장명, 주소, 가격, 리뷰 개수, 가장 최신 리뷰, 주차 구역 목록
     *  ---------------------
     * 2024.09.07 양건모 | 기능 구현
     * */
    @GetMapping("/preview/{parkingLotId}")
    public ResponseEntity<ParkingLotDto.ParkingLotPreviewResponseDto> getParkingLotPreview(@PathVariable Long parkingLotId) {
        return ResponseEntity.ok(parkingLotService.getParkingLotPreview(parkingLotId));
    }

}
