package org.gomgom.parkingplace.Service.plateNumber;

import org.gomgom.parkingplace.Dto.PlateNumberDto;
import org.gomgom.parkingplace.Entity.User;

import java.util.Map;

public interface PlateNumberService {

    // 내 차량 목록 가져오기
    Map<String, Object> getPlateNumber(User user);

    // 차량 등록하기
    void registerPlateNumber(User user, PlateNumberDto.RequestMyCarDto carDto);

    // 차량 삭제하기
    void deletePlateNumber(User user, PlateNumberDto.RequestMyCarDto carDto);
}
