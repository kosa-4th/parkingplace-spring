package org.gomgom.parkingplace.Service.carType;

import org.gomgom.parkingplace.Dto.CarTypeDto;

public interface CarTypeService {

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.20
     * 설명 : 모든 차량 유형에 대해 id와 유형명 반환
     *  ---------------------
     * 2024.09.20 양건모 | 기능 구현
     * */
    CarTypeDto.AllCarTypeSelectResponseDto getAllCarTypes();
}
