package org.gomgom.parkingplace.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gomgom.parkingplace.Entity.ParkingImage;
import org.gomgom.parkingplace.Entity.ParkingLot;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
    public static class ParkingLotMarkersDto {
        private final List<ParkingLotMarkerDto> lots;
    }

    @Getter
    public static class ParkingLotMarkerDto {
        private final long id;
        private final double latitude;
        private final double longitude;
        private final String name;
        private final String address;

        ParkingLotMarkerDto(ParkingLot parkingLot) {
            this.id = parkingLot.getId();
            this.latitude = parkingLot.getLatitude();
            this.longitude = parkingLot.getLongitude();
            this.name = parkingLot.getName();
            this.address = parkingLot.getAddress();
        }
    }

    /*
    작성자: 오지수
    주차장 상세 페이지 : 메인, 주차장 정보
     */
    @Getter
    public static class ParkingLotDetailResponseDto {
        private String parkingLotName;
        private String parkingLotType;

        private String address;
        private String phone;
        private String weekdayTime;
        private String weekendTime;
        private int weekdayPrice;
        private int weekendPrice;
        private final List<String> images;

        public ParkingLotDetailResponseDto(ParkingLot parkingLot) {
            this.parkingLotName = parkingLot.getName();
            this.parkingLotType = "주차장 종류";
            this.address = parkingLot.getAddress();
            this.phone = "02-0000-0000";
            this.weekdayTime = parkingLot.getWeekdaysOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")) +
                    " ~ " + parkingLot.getWeekdaysCloseTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            this.weekendTime = parkingLot.getWeekendOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")) +
                    " ~ " + parkingLot.getWeekendCloseTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            this.weekdayPrice = parkingLot.getParkingSpaces().getFirst().getWeekdaysPrice();
            this.weekendPrice = parkingLot.getParkingSpaces().getFirst().getWeekendPrice();
            this.images = parkingLot.getParkingImages().stream()
                    .map(ParkingImage::getImagePath)
                    .collect(Collectors.toList());
        }
    }


}