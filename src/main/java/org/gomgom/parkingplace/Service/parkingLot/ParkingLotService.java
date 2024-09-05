package org.gomgom.parkingplace.Service.parkingLot;

import org.gomgom.parkingplace.Dto.ParkingLotDto;


public interface ParkingLotService {

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.02
     * 설명 : 위도 경도 범위 내의 주차장들의 위도, 경도를 포함한 정보 제공
     * @param request 최소 위도, 최대 위도, 최소 경도, 최대 경도
     * @return List 형태로 id, 이름, 위도, 경도, 주소
     *  ---------------------
     * 2024.09.05 양건모 | 기능 구현
     * */
    ParkingLotDto.ParkingLotMarkersDto getParkingLots(ParkingLotDto.ParkingLotListRequestDto request);

    /*
    작성자: 오지수
    주차장 상세 정보 가져오기
     */
    ParkingLotDto.ParkingLotDetailResponseDto getParkingLotDetail(Long parkingLotId);
}
