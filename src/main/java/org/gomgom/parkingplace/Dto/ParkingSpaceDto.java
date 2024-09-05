package org.gomgom.parkingplace.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gomgom.parkingplace.Entity.ParkingSpace;

public class ParkingSpaceDto {

    @Getter
    public static class ParkingSpaceListResponseDto {
//        private String spaceName;
        private final Integer weekdaysPrice;
        private final Integer weekendPrice;
        private final Integer weekAllDayPrice;
        private final Integer weekendAllDayPrice;
        private final String carType;

        public ParkingSpaceListResponseDto(ParkingSpace parkingSpace) {
            this.weekdaysPrice = parkingSpace.getWeekdaysPrice();
            this.weekendPrice = parkingSpace.getWeekendPrice();
            this.weekAllDayPrice = parkingSpace.getWeekAllDayPrice();
            this.weekendAllDayPrice = parkingSpace.getWeekendAllDayPrice();
            this.carType = parkingSpace.getCarType().getCarType();
        }
    }
}
