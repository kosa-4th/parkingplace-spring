package org.gomgom.parkingplace.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.enums.Bool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author 김경민
 * @Date 2024-09-03
 *
 * ReservationDto 생성
 *
 * */
public class ReservationDto {

    // 클라이언트가 예약 요청 시 사용하는 DTO
    @Data
    @AllArgsConstructor
    @ToString
    public static class RequestReservationDto {
        private Long parkingLotId;
        private Long parkingSpaceId;
        private String userEmail;
        private String plateNumber;
        private String startTime;
        private String endTime;
        private Integer totalPrice;
        private Bool washService;
        private Bool maintenanceService;

    }

    @Data
    @AllArgsConstructor
    public static class RequestAvailableDto {
        private Long parkingLotId;
        private CarType carType;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Bool washService;
        private Bool maintenanceService;
    }

    // 서버가 예약 정보를 응답할 때 사용하는 DTO
    @AllArgsConstructor
    @Getter
    public static class ResponseReservationDto {
        private Long reservationId;
        private String reservationUuid;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Integer totalPrice;
        private Bool reservationConfirmed;
        private String plateNumber;
        private Bool wash;
        private Bool maintenance;
        private String parkingLotName;
        private String parkingSpaceName;
        private String userEmail;
        private String userName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        // Reservation 엔티티로부터 DTO를 생성하는 생성자 추가
        public ResponseReservationDto(Reservation reservation) {
            this.reservationId = reservation.getId();
            this.reservationUuid = reservation.getReservationUuid();
            this.startTime = reservation.getStartTime();
            this.endTime = reservation.getEndTime();
            this.totalPrice = reservation.getTotalPrice();
            this.reservationConfirmed = reservation.getReservationConfirmed();
            this.plateNumber = reservation.getPlateNumber();
            this.wash = reservation.getWash();
            this.maintenance = reservation.getMaintenance();
            this.parkingLotName = reservation.getParkingLot().getName();  // 주차장 이름
            this.parkingSpaceName = reservation.getParkingSpace().getId().toString();  // 주차 공간 이름 (예시)
            this.userEmail = reservation.getUser().getEmail();  // 사용자 이메일
            this.userName = reservation.getUser().getName();    // 사용자 이름
            this.createdAt = reservation.getCreatedAt();
            this.updatedAt = reservation.getUpdatedAt();
        }
    }
    @AllArgsConstructor
    @Getter
    public static class ReservationAvailableResponseDto{
        private boolean available;
        private int totalFee;
        private Long parkingSpaceId;

    }

}
