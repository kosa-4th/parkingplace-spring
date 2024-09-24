package org.gomgom.parkingplace.Dto;

import lombok.*;
import org.gomgom.parkingplace.Entity.ParkingImage;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.ParkingSpace;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParkingLotDto {


    /**@Author 김경민
     * @Date 2024.09.24
     * 주차장 데이터 수정
     */
    @Data
    public static class RequestModifyLotDto{
        private Long id;                 // 주차장 ID
        private String name;             // 주차장 이름
        private String tel;              // 전화번호
        private String parkingType;      // 주차장 타입
        private double latitude;
        private double longitude;
        private LocalTime weekdaysOpenTime;  // 평일 오픈 시간
        private LocalTime weekdaysCloseTime; // 평일 닫는 시간
        private LocalTime weekendOpenTime;   // 주말 오픈 시간
        private LocalTime weekendCloseTime;  // 주말 닫는 시간
        private Bool wash;               // 세차 서비스 여부
        private Bool maintenance;        // 정비 서비스 여부
        private String userEmail;

        // 빈 문자열을 null로 변환하는 메서드
        public void trimFields() {
            if (name != null && name.trim().isEmpty()) {
                name = null;
            }
            if (tel != null && tel.trim().isEmpty()) {
                tel = null;
            }
            if (parkingType != null && parkingType.trim().isEmpty()) {
                parkingType = null;
            }
            if (userEmail != null && userEmail.trim().isEmpty()) {
                userEmail = null;
            }
            if (latitude == 0.0) {
                this.latitude = -1.0;
            }
            if (longitude == 0.0) {
                this.longitude = -1.0;
            }
        }
    }


    /**
     * @Author 김경민
     * @Date 2024.09.23
     *
     * 주차장 데이터
     * */
    @Getter
    @Setter
    public static class ResponseParkingLotDto {
        private Long id;                 // 주차장 ID
        private String name;             // 주차장 이름
        private String address;          // 주소
        private String tel;              // 전화번호
        private String parkingType;      // 주차장 타입
        private Double latitude;         // 위도
        private Double longitude;        // 경도
        private LocalTime weekdaysOpenTime;  // 평일 오픈 시간
        private LocalTime weekdaysCloseTime; // 평일 닫는 시간
        private LocalTime weekendOpenTime;   // 주말 오픈 시간
        private LocalTime weekendCloseTime;  // 주말 닫는 시간
        private Bool wash;               // 세차 서비스 여부
        private Bool maintenance;        // 정비 서비스 여부
        private Bool usable;             // 사용 가능 여부
        private UserDto.ResponseAllUserDto user;

        public ResponseParkingLotDto(Long id, String name, String address, String tel,
                                     String parkingType, Double latitude, Double longitude,
                                     LocalTime weekdaysOpenTime, LocalTime weekdaysCloseTime,
                                     LocalTime weekendOpenTime, LocalTime weekendCloseTime,
                                     Bool wash, Bool maintenance, Bool usable, UserDto.ResponseAllUserDto user) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.tel = tel;
            this.parkingType = parkingType;
            this.latitude = latitude;
            this.longitude = longitude;
            this.weekdaysOpenTime = weekdaysOpenTime;
            this.weekdaysCloseTime = weekdaysCloseTime;
            this.weekendOpenTime = weekendOpenTime;
            this.weekendCloseTime = weekendCloseTime;
            this.wash = wash;
            this.maintenance = maintenance;
            this.usable = usable;
            this.user = (user != null) ? user : new UserDto.ResponseAllUserDto();  // user가 null일 때 기본 객체로 초기화
        }
    }

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
     * 2024.09.09 양건모 | 본 서비스를 통해 예약 가능한 주차장인지 여부를 판단하기 위한 hasUser 컬럼 추가
     */
    @Getter
    public static class ParkingLotMarkerDto {
        private final long id;
        private final double latitude;
        private final double longitude;
        private final String name;
        private final String address;
        private final boolean hasUser;

        ParkingLotMarkerDto(long id, double latitude, double longitude, String name, String address, boolean hasUser) {
            this.id = id;
            this.latitude = latitude;
            this.longitude = longitude;
            this.name = name;
            this.address = address;
            this.hasUser = hasUser;
        }
    }

    /**
     * 작성자: 오지수
     * ? : 주차장 상세 페이지에 전달할 주차장 정보
     * String parkingLotName: 주차장 이름
     * String parkingLotType: 주차장 유형
     * String address: 주차장 주소
     * String phone: 주차장 전화번호
     * String weekdayTime: 평일 주차장 운영시간
     * String weekendTime: 주말 주차장 운영시간
     * int weekdayPrice: 평일 가격
     * int weekendPrice: 주말 가격
     * List<String> images: 주차장 이미지
     */
    @Getter
    public static class ParkingLotDetailResponseDto {
        private String parkingLotName;
        private String parkingLotType;
        private boolean isIfReservation;

        private String address;
        private String phone;
        private String weekdayTime;
        private String weekendTime;
        private int weekdayPrice;
        private int weekendPrice;
        private List<String> images;

        public ParkingLotDetailResponseDto(ParkingLot parkingLot) {
            this.parkingLotName = parkingLot.getName();
            this.parkingLotType = parkingLot.getParkingType();
            this.isIfReservation = Optional.ofNullable(parkingLot.getUser()).isPresent();
            this.address = parkingLot.getAddress();
            this.phone = parkingLot.getTel();
            this.weekdayTime = parkingLot.getWeekdaysOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")) +
                    " ~ " + parkingLot.getWeekdaysCloseTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            this.weekendTime = parkingLot.getWeekendOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")) +
                    " ~ " + parkingLot.getWeekendCloseTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            this.weekdayPrice = parkingLot.getParkingSpaces().getFirst().getWeekdaysPrice();
            this.weekendPrice = parkingLot.getParkingSpaces().getFirst().getWeekendPrice();
            this.images = Optional.ofNullable(parkingLot.getParkingImages())
                    .orElse(Collections.emptyList())
                    .stream()
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
     * 2024.09.09 양건모 | 본 서비스를 통해 예약 가능한 주차장인지 여부를 판단하기 위한 hasUser 컬럼 추가
     */
    @Getter
    public static class ParkingLotPreviewResponseDto {
        private final String name;
        private final String address;
        private final long reviewCount;
        private final String recentReview;
        private final boolean hasUser;
        private List<ParkingSpaceDto.ParkingSpacesPreviewDto> parkingSpaces;

        public ParkingLotPreviewResponseDto(String name, String address, long reviewCount, String recentReview, boolean hasUser) {
            this.name = name;
            this.address = address;
            this.reviewCount = reviewCount;
            this.recentReview = recentReview;
            this.hasUser = hasUser;
        }

        public void setParkingSpaces(List<ParkingSpaceDto.ParkingSpacesPreviewDto> parkingSpaces) {
            this.parkingSpaces = parkingSpaces;
        }
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.15
     * 설명 : 주차장 업주의 등록한 주차장 목록 조회 요소 DTO
     * ---------------------
     * 2024.09.15 양건모 | 기능 구현
     */
    @RequiredArgsConstructor
    @Getter
    public static class ParkingLotIdAndNameDto {
        private final long id;
        private final String name;
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.15
     * 설명 : 주차장 업주의 등록한 주차장 목록 조회 응답 DTO
     * ---------------------
     * 2024.09.15 양건모 | 기능 구현
     */
    @RequiredArgsConstructor
    @Getter
    public static class MyParkingLotsReponseDto {
        private final List<ParkingLotDto.ParkingLotIdAndNameDto> parkingLots;
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.18
     * 설명 : 주차장 업주의 등록한 주차장 상세 조회 응답 DTO
     * ---------------------
     * 2024.09.18 양건모 | 기능 구현
     */
    @RequiredArgsConstructor
    @Getter
    public static class OwnerParkingLotDetailResponseDto {
        private final String name;
        private final String address;
        private final String tel;
        private final LocalTime weekdaysOpenTime;
        private final LocalTime WeekdaysCloseTime;
        private final LocalTime weekendOpenTime;
        private final LocalTime weekendCloseTime;
        private final List<MyParkingLotImageDto> images;
        private final List<MyParkingLotSpaceDto> parkingSpaces;
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.18
     * 설명 : 주차장 업주의 등록한 주차장 상세 조회의 이미지 요소 DTO
     * ---------------------
     * 2024.09.18 양건모 | 기능 구현
     */
    @RequiredArgsConstructor
    @Getter
    public static class MyParkingLotImageDto {
        private final long id;
        private final String path;
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.18
     * 설명 : 주차장 업주의 등록한 주차장 상세 조회의 주차 공간 요소 DTO
     * ---------------------
     * 2024.09.18 양건모 | 기능 구현
     */
    @RequiredArgsConstructor
    @Getter
    public static class MyParkingLotSpaceDto {
        private final long id;
        private final String spaceName;
        private final String carType;
        private final int weekdaysPrice;
        private final int weekendPrice;
        private final int weekAllDayPrice;
        private final int weekendAllDayPrice;
        private final Integer washPrice;
        private final Integer maintenancePrice;
        private final int availableSpaceNum;
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.18
     * 설명 : 주차장 정보 수정을 위한 요청 DTO
     * ---------------------
     * 2024.09.18 양건모 | 기능 구현
     * 2024.09.20 양건모 | 기존 이미지 삭제를 위해 deleteImageIds 컬럼 추가
     */
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ParkingLotModifyRequestDto {
        private String name;
        private String tel;
        private LocalTime weekdaysOpenTime;
        private LocalTime weekdaysCloseTime;
        private LocalTime weekendOpenTime;
        private LocalTime weekendCloseTime;
        private List<Long> deleteImageIds;
        private MultipartFile[] images;
    }

}
