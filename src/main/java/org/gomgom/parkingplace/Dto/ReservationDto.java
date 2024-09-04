package org.gomgom.parkingplace.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.gomgom.parkingplace.enums.Bool;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public static class RequestReservationDto {
        private Long parkingLotId;
        private Long userId;
        private Long plateNumberId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Integer totalPrice;
        private Bool wash;
        private Bool maintenance;
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
        private String userEmail;
    }

}
