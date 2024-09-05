package org.gomgom.parkingplace.Service.parkingLot;


import org.gomgom.parkingplace.Dto.ParkingLotDto;

import java.util.List;

/**
 * 생성 일자: 2024.09.05
 * 설명 : 주차장 관련 서비스
 * ---------------------
 * 2024.09.05 양건모 | getParkingLots(): 특정 위도 경도 범위 내의 주차장 목록 조회
 * */

public interface ParkingLotService {

    ParkingLotDto.ParkingLotMarkersDto getParkingLots(ParkingLotDto.ParkingLotListRequestDto request);

    /*
    작성자: 오지수
    주차장 상세 정보 가져오기
     */
    ParkingLotDto.ParkingLotDetailResponseDto getParkingLotDetail(Long parkingLotId);
}
