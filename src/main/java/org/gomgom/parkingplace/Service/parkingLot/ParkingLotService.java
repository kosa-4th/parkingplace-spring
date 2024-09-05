package org.gomgom.parkingplace.Service.parkingLot;

import org.gomgom.parkingplace.Dto.ParkingLotDto;


public interface ParkingLotService {

    ParkingLotDto.ParkingLotMarkersDto getParkingLots(ParkingLotDto.ParkingLotListRequestDto request);

    /*
    작성자: 오지수
    주차장 상세 정보 가져오기
     */
    ParkingLotDto.ParkingLotDetailResponseDto getParkingLotDetail(Long parkingLotId);
}
