package org.gomgom.parkingplace.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

public class ParkingLotDto {

    @Getter
    @AllArgsConstructor
    public static class ParkingLotListRequestDto {
        private double minLat;
        private double maxLat;
        private double minLon;
        private double maxLon;
    }

    @Getter
    @AllArgsConstructor
    public static class ParkingLotListResponseDto {
        private double latitude;
        private double longitude;
        private String name;
        private String address;
        private LocalTime weekOpenTime;
        private LocalTime weekCloseTime;
        private LocalTime weekendOpenTime;
        private LocalTime weekendCloseTime;
        private List<Object> parkingSpaces;
    }

}
