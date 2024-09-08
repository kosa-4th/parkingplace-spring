package org.gomgom.parkingplace.Dto;

import lombok.Getter;
import org.gomgom.parkingplace.enums.Bool;

import java.time.LocalTime;
import java.util.List;

/**
 * @Author 김경민
 * @date 2024.09.07
 * 예약하기 위한 주차장 정보 및 자동차 번호에 맞는 차량타입 받아올 DTO
 * */

public class ParkingLotAndCarInfoDto  {
    @Getter
    public static class ParkingLotReservationResponseDto {
        private String name;
        private Bool wash;
        private Bool maintenance;
        private LocalTime weekdaysOpenTime;
        private LocalTime weekendOpenTime;
        private LocalTime weekdaysCloseTime;
        private LocalTime weekendCloseTime;
        private Long userId;

        // 생성자
        public ParkingLotReservationResponseDto(String name, Bool wash, Bool maintenance, LocalTime weekdaysOpenTime, LocalTime weekendOpenTime, LocalTime weekdaysCloseTime, LocalTime weekendCloseTime, Long userId) {
            this.name = name;
            this.wash = wash;
            this.maintenance = maintenance;
            this.weekdaysOpenTime = weekdaysOpenTime;
            this.weekendOpenTime = weekendOpenTime;
            this.weekdaysCloseTime = weekdaysCloseTime;
            this.weekendCloseTime = weekendCloseTime;
            this.userId = userId;
        }
    }
    // 자동차 번호 DTO
    @Getter
    public static class PlateNumberDto {
        private String plateNumber;
        private Long carTypeId;

        // 생성자
        public PlateNumberDto(String plateNumber, Long carTypeId) {
            this.plateNumber = plateNumber;
            this.carTypeId = carTypeId;
        }
    }
    // 주차장 정보와 자동차 리스트를 포함한 통합 DTO
    @Getter
    public static class ParkingLotAndCarInfoResponseDto {
        private ParkingLotReservationResponseDto parkingLotInfo; // 주차장 정보
        private List<PlateNumberDto> userCars;  // 회원의 자동차 번호 리스트

        // 생성자
        public ParkingLotAndCarInfoResponseDto(ParkingLotReservationResponseDto parkingLotInfo, List<PlateNumberDto> userCars) {
            this.parkingLotInfo = parkingLotInfo;
            this.userCars = userCars;
        }
    }
}
