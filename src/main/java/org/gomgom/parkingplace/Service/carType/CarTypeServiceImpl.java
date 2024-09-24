package org.gomgom.parkingplace.Service.carType;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.CarTypeDto;
import org.gomgom.parkingplace.Repository.CarTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarTypeServiceImpl implements CarTypeService{

    private final CarTypeRepository carTypeRepository;

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.20
     * 설명 : 모든 차량 유형에 대해 id와 유형명 반환
     *  ---------------------
     * 2024.09.20 양건모 | 기능 구현
     * */
    @Override
    public CarTypeDto.AllCarTypeSelectResponseDto getAllCarTypes() {
        List<CarTypeDto.CarTypeSelectDto> list = carTypeRepository.getAllCarTypes();
        return new CarTypeDto.AllCarTypeSelectResponseDto(list);
    }
}
