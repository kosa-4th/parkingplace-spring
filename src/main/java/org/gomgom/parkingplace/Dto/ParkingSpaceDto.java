package org.gomgom.parkingplace.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.Entity.ParkingLot;
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
     * 2024.09.09 양건모 | carType 반환타입 carTypeEnum -> String 변경
     * */
    @Getter
    public static class ParkingSpacesPreviewDto {
        //private final String spaceName;
        private final String carType;
        private final int weekdaysPrice;
        private final int weekendPrice;
        private final int weekAllDayPrice;
        private final int weekendAllDayPrice;

        public ParkingSpacesPreviewDto(CarTypeEnum carType, int weekdaysPrice, int weekendPrice, int weekAllDayPrice, int weekendAllDayPrice) {
            //this.spaceName = parkingSpace.getSpaceName();
            this.carType = carType.getKor();
            this.weekdaysPrice = weekdaysPrice;
            this.weekendPrice = weekendPrice;
            this.weekAllDayPrice = weekAllDayPrice;
            this.weekendAllDayPrice = weekendAllDayPrice;
        }
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.21
     * 설명 : 주차 구역 값 입력을 위한 DTO
     *  ---------------------
     * 2024.09.21 양건모 | 기능 구현
     * 2024.09.21 양건모 | 세차서비스, 정비 서비스 지원 여부 확인을 위한 washService, maintenanceService 필드 추가
     * */
    @RequiredArgsConstructor
    @Getter
    public static class InputParkingSpaceValuesDto {
        private final String spaceName;
        private final int availableSpaceNum;
        private final long carTypeId;
        private final int weekdaysPrice;
        private final int weekAllDayPrice;
        private final int weekendPrice;
        private final int weekendAllDayPrice;
        private final boolean washService;
        private final Integer washPrice;
        private final boolean maintenanceService;
        private final Integer maintenancePrice;
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.20
     * 설명 : 주차 구역 추가를 위한 요청 DTO
     *  ---------------------
     * 2024.09.20 양건모 | 기능 구현
     * 2024.09.21 양건모 | 수정 요청 DTO와 중복되는 핃드들을 별도의 DTO로 분리
     * */
    @Getter
    public static class InsertParkingSpaceRequestDto extends InputParkingSpaceValuesDto {
        private final long parkingLotId;

        public InsertParkingSpaceRequestDto(long parkingLotId, String spaceName, int availableSpaceNum,
                                            long carTypeId, int weekdaysPrice, int weekAllDayPrice,
                                            int weekendPrice, int weekendAllDayPrice, boolean washService,
                                            Integer washPrice, boolean maintenanceService, Integer maintenancePrice) {
            super(spaceName, availableSpaceNum, carTypeId, weekdaysPrice, weekAllDayPrice, weekendPrice, weekendAllDayPrice, washService, washPrice, maintenanceService, maintenancePrice);
            this.parkingLotId = parkingLotId;
        }
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.21
     * 설명 : 주차 구역 수정을 위한 요청 DTO
     *  ---------------------
     * 2024.09.21 양건모 | 기능 구현
     * */
    @Getter
    public static class ModifyParkingSpaceRequestDto extends InputParkingSpaceValuesDto {
        private final long parkingSpaceId;


        public ModifyParkingSpaceRequestDto(long parkingSpaceId, String spaceName, int availableSpaceNum,
                                            long carTypeId, int weekdaysPrice, int weekAllDayPrice,
                                            int weekendPrice, int weekendAllDayPrice, boolean washService,
                                            Integer washPrice, boolean maintenanceService, Integer maintenancePrice) {
            super(spaceName, availableSpaceNum, carTypeId, weekdaysPrice, weekAllDayPrice, weekendPrice, weekendAllDayPrice, washService, washPrice, maintenanceService, maintenancePrice);
            this.parkingSpaceId = parkingSpaceId;
        }
    }
}
