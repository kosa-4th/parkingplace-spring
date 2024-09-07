package org.gomgom.parkingplace.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gomgom.parkingplace.Entity.ParkingSpace;
import org.gomgom.parkingplace.enums.CarTypeEnum;

public class ParkingSpaceDto {
    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.07
     * 설명 : 주차장 간략 정보 제공을 위한 주차 구역 DTO
     *  ---------------------
     * 2024.09.07 양건모 | 기능 구현
     * 2024.09.07 양건모 | CarType.carType 타입 변경에 대한 수정
     * */
    @Getter
    public static class ParkingSpacesPreviewDto {
        //private final String spaceName;
        private final CarTypeEnum carType;
        private final int weekdaysPrice;
        private final int weekendPrice;
        private final int weekAllDayPrice;
        private final int weekendAllDayPrice;

        public ParkingSpacesPreviewDto(CarTypeEnum carType, int weekdaysPrice, int weekendPrice, int weekAllDayPrice, int weekendAllDayPrice) {
            //this.spaceName = parkingSpace.getSpaceName();
            this.carType = carType;
            this.weekdaysPrice = weekdaysPrice;
            this.weekendPrice = weekendPrice;
            this.weekAllDayPrice = weekAllDayPrice;
            this.weekendAllDayPrice = weekendAllDayPrice;
        }
    }
}
