package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.gomgom.parkingplace.Service.parkingLot.ParkingLotService;
import org.gomgom.parkingplace.Service.parkingSpace.ParkingSpaceService;
import org.gomgom.parkingplace.enums.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.16
     * 설명 : 나의 주차장 목록
     * @return 주차장 id, 주차장명
     *  ---------------------
     * 2024.09.16 양건모 | 기능 구현
     * */
    @GetMapping("/my/protected")
    public ResponseEntity<ParkingLotDto.MyParkingLotsReponseDto> getMyParkingLots (
            @AuthenticationPrincipal CustomUserDetails userDetails) throws BadRequestException {
        if (userDetails.getUser().getAuth() != Role.ROLE_PARKING_MANAGER) {
            throw new BadRequestException("접근 권한이 없습니다");
        }
        return ResponseEntity.ok(parkingLotService.getMyParkingLots(userDetails.getUser().getId()));
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.18
     * 설명 : 업주 자신의 주차장 세부 정보 조회
     * @param parkingLotId 주차장 id
     * @return 주차장 세부 정보
     *  ---------------------
     * 2024.09.18 양건모 | 기능 구현
     * */
    @GetMapping("/{parkingLotId}/owner/protected")
    @PreAuthorize("hasRole('ROLE_PARKING_MANAGER')")
    public ResponseEntity<ParkingLotDto.OwnerParkingLotDetailResponseDto> getOwnerParkingLotDetail (
            @PathVariable long parkingLotId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws BadRequestException {
        return ResponseEntity.ok(parkingLotService.getOwnerParkingLotDetail(userDetails.getUser().getId(), parkingLotId));
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.19
     * 설명 : 나의 주차장 수정
     * @param parkingLotId 주차장 id
     * @pararm request 수정될 값
     * @return void
     *  ---------------------
     * 2024.09.19 양건모 | 기능 구현
     * 2024.09.19 양건모 | 원활한 트랜잭션 처리 및 영속성 반영을 위해 수정과 반환 로직을 분리
     * */
    @PutMapping("/{parkingLotId}/owner/protected")
    @PreAuthorize("hasRole('ROLE_PARKING_MANAGER')")
    public ResponseEntity<ParkingLotDto.OwnerParkingLotDetailResponseDto> modifyOwnerParkingLotDetail (
            @PathVariable long parkingLotId,
            @ModelAttribute ParkingLotDto.ParkingLotModifyRequestDto request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws IOException, BadRequestException {
        parkingLotService.modifyOwnerParkingLotDetail(userDetails.getUser().getId(), parkingLotId, request);
        return ResponseEntity.ok(parkingLotService.getOwnerParkingLotDetail(userDetails.getUser().getId(), parkingLotId));
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.19
     * 설명 : 나의 주차장 수정
     * @param parkingLotId 주차장 id
     * @pararm request 수정될 값
     * @return void
     *  ---------------------
     * 2024.09.19 양건모 | 기능 구현
     * 2024.09.19 양건모 | 원활한 트랜잭션 처리 및 영속성 반영을 위해 수정과 반환 로직을 분리
     * */
    @GetMapping("/recommend")
    public ResponseEntity<ParkingLotDto.RecommendedParkingLotsResponseDto> getRecommendedParkingLots(
            @RequestBody ParkingLotDto.RecommendedParkingLotsRequestDto request
    ) {
        return ResponseEntity.ok(parkingLotService.getRecommendedPakringLots(request));
    }
}
