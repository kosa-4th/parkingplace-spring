package org.gomgom.parkingplace.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Entity.CarType;

import java.util.List;

public class CarTypeDto {

    @Getter
    public static class CarTypeSelectDto {
        private final long id;
        private final String carTypeKor;

        public CarTypeSelectDto(CarType carType) {
            this.id = carType.getId();
            this.carTypeKor = carType.getCarTypeEnum().getKor();
        }
    }

    @RequiredArgsConstructor
    @Getter
    public static class AllCarTypeSelectResponseDto {
        private final List<CarTypeSelectDto> carTypes;
    }
}
