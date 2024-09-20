package org.gomgom.parkingplace.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Author 김경민
 * @Date 2024-09-03
 * @Date 2024-09-13 코드수정
 *
 * ReservationDto 생성
 *
 * */
public class ReservationDto {
    /**
     * @Author 김경민
     * @Date 2024.09.19
     */
    @Data
    @AllArgsConstructor
    public static class RequestOwnerReservationDto{
        private Long parkingLotId;
        private Bool reservationConfirmed;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
    /**
     * @Author 김경민
     * @date 2024.09.19
     * 관리자 전용 예약 관리 Response
     * */
    @Getter
    @ToString
    public static class ResponseOwnerReservationDto{
        private Long reservationId;
        private String userName; //유저이름
        private String userEmail;
        private String plateCarNumber; //자동차번호
        private String spaceName; //주차장좌석이름
        private String reservationUid;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Bool reservationConfirmed; //예약상태
        private Bool wash;
        private Bool maintenance;

        public ResponseOwnerReservationDto(Long reservationId, String userName, String userEmail, String plateCarNumber, String spaceName, String reservationUid, LocalDateTime startTime, LocalDateTime endTime, Bool reservationConfirmed, Bool wash, Bool maintenance) {
            this.reservationId = reservationId;
            this.userName = userName;
            this.userEmail = userEmail;
            this.plateCarNumber = plateCarNumber;
            this.spaceName = spaceName;
            this.reservationUid = reservationUid;
            this.startTime = startTime;
            this.endTime = endTime;
            this.reservationConfirmed = reservationConfirmed;
            this.wash = wash;
            this.maintenance = maintenance;
        }
    }

    // 클라이언트가 예약 요청 시 사용하는 DTO
    @Data
    @ToString
    public static class RequestReservationDto {
        private Long parkingSpaceId;
        private String plateNumber;
        private String startTime;
        private String endTime;
        private Integer totalPrice;
        private Bool washService;
        private Bool maintenanceService;



        public RequestReservationDto(Long parkingSpaceId, String plateNumber, String startTime, String endTime, Integer totalPrice, String wash, String maintenance) {
            this.parkingSpaceId = parkingSpaceId;
            this.plateNumber = plateNumber;
            this.startTime = startTime;
            this.endTime = endTime;
            this.totalPrice = totalPrice;
            this.washService = "true".equalsIgnoreCase(wash) ? Bool.Y : Bool.N;
            this.maintenanceService = "true".equalsIgnoreCase(maintenance) ? Bool.Y : Bool.N;
        }

    }

    /**
     *  @DATE 2024.09.13 -> 컨트롤을 바꾸면서 ModelAttribute로 바꾸면서 코드 리팩토링
     * */
    @Data
    public static class RequestAvailableDto {
        private String plateNumber;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime startTime;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime endTime;

        private Bool washService;
        private Bool maintenanceService;

        public RequestAvailableDto(String plateNumber, LocalDateTime startTime, LocalDateTime endTime, String wash, String maintenance) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.plateNumber = plateNumber;
            this.washService = "true".equalsIgnoreCase(wash) ? Bool.Y : Bool.N;
            this.maintenanceService = "true".equalsIgnoreCase(maintenance) ? Bool.Y : Bool.N;
        }
    }

    // 서버가 예약 정보를 응답할 때 사용하는 DTO
    @AllArgsConstructor
    @Getter
    public static class  ResponseReservationDto {
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
