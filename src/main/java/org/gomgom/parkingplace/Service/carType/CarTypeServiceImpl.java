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

    @Override
    public CarTypeDto.AllCarTypeSelectResponseDto getAllCarTypes() {
        List<CarTypeDto.CarTypeSelectDto> list = carTypeRepository.getAllCarTypes();
        return new CarTypeDto.AllCarTypeSelectResponseDto(list);
    }
}
