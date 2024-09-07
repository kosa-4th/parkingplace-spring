package org.gomgom.parkingplace.Service.parkingLot;

import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.gomgom.parkingplace.Dto.ParkingLotDto.ParkingLotPreviewResponseDto;

import java.util.Optional;


public interface ParkingLotService {

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.02
     * 설명 : 위도 경도 범위 내의 주차장들의 위도, 경도를 포함한 정보 제공
     * @param request 최소 위도, 최대 위도, 최소 경도, 최대 경도
     * @return List 형태로 id, 이름, 위도, 경도, 주소
     *  ---------------------
     * 2024.09.05 양건모 | 기능 구현
     * 2024.09.07 양건모 | 반환 DTO 이름 변경에 대한 적용
     * */
    ParkingLotDto.ParkingLotMarkersResponseDto getParkingLots(ParkingLotDto.ParkingLotListRequestDto request);

    /*
    작성자: 오지수
    주차장 상세 정보 가져오기
     */
    ParkingLotDto.ParkingLotDetailResponseDto getParkingLotDetail(Long parkingLotId);

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.07
     * 설명 : 주차장의 간략한 정보 제공
     * @param parkingLotId 주차장 id
     * @return 주차장명, 주소, 가격, 리뷰 개수, 가장 최신 리뷰, 주차 구역 목록
     *  ---------------------
     * 2024.09.07 양건모 | 기능 구현
     * */
    ParkingLotDto.ParkingLotPreviewResponseDto getParkingLotPreview(Long parkingLotId);
}
