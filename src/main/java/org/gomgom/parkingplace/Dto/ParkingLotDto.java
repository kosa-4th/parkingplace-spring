package org.gomgom.parkingplace.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gomgom.parkingplace.Entity.ParkingImage;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.ParkingSpace;
import org.gomgom.parkingplace.enums.Bool;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLotDto {

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.05
     * 설명 : 주차장 마커 생성을 위한 요청 DTO
     * ---------------------
     * 2024.09.05 양건모 | 기능 구현
     */
    @Getter
    @AllArgsConstructor
    public static class ParkingLotListRequestDto {
        private double minLat;
        private double maxLat;
        private double minLon;
        private double maxLon;
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.05
     * 설명 : 주차장 마커 생성을 위한 응답 DTO
     * ---------------------
     * 2024.09.05 양건모 | 기능 구현
     * 2024.09.07 양건모 | 응답용 Dto임을 알 수 있게 이름 변경: ParkingLotMarkersDto -> ParkingLotMarkersResponseDto
     */
    @Getter
    @AllArgsConstructor
    public static class ParkingLotMarkersResponseDto {
        private final List<ParkingLotMarkerDto> lots;
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.05
     * 설명 : 주차장 마커 DTO
     * ---------------------
     * 2024.09.05 양건모 | 기능 구현
     */
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

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.07
     * 설명 : 주차장 간략 정보 제공을 위한 응답 DTO
     * ---------------------
     * 2024.09.07 양건모 | 기능 구현
     */
    @Getter
    public static class ParkingLotPreviewResponseDto {
        private final String name;
        private final String address;
        private final long reviewCount;
        private final String recentReview;
        private List<ParkingSpaceDto.ParkingSpacesPreviewDto> parkingSpaces;

        public ParkingLotPreviewResponseDto(String name, String address, long reviewCount, String recentReview) {
            this.name = name;
            this.address = address;
            this.reviewCount = reviewCount;
            this.recentReview = recentReview;
        }

        public void setParkingSpaces(List<ParkingSpaceDto.ParkingSpacesPreviewDto> parkingSpaces) {
            this.parkingSpaces = parkingSpaces;
        }
    }

}
