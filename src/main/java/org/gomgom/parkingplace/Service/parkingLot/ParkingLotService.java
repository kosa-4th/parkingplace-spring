package org.gomgom.parkingplace.Service.parkingLot;


import org.gomgom.parkingplace.Dto.ParkingLotDto;

import java.util.List;

public interface ParkingLotService {
    //List<ParkingLotDto.ParkingLotListResponseDto> getParkingLots(ParkingLotDto.ParkingLotListRequestDto);

    /*
    작성자: 오지수
    주차장 상세 정보 가져오기
     */
    public ParkingLotDto.ParkingLotDetailResponseDto getParkingLotDetail(Long parkingLotId);
}
