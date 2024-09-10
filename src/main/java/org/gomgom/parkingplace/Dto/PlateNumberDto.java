package org.gomgom.parkingplace.Dto;

import lombok.Getter;
import org.gomgom.parkingplace.Entity.PlateNumber;

public class PlateNumberDto {

    @Getter
    public static class ResponseMyCarDto {
        private final Long id;
        private final String carType;
        private final String plateNumber;

        public ResponseMyCarDto(PlateNumber plateNumber) {
            this.id = plateNumber.getId();
            this.carType = plateNumber.getCarType().getCarTypeEnum().getKor();
            this.plateNumber = plateNumber.getPlateNumber();
        }
    }

    @Getter
    public static class RequestMyCarDto {
        private String carType;
        private String plateNumber;
    }
}
