package org.gomgom.parkingplace.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gomgom.parkingplace.Entity.Inquiry;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.Entity.Review;
import org.gomgom.parkingplace.enums.Bool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MyPageDto {

    /**
     * 작성자: 오지수
     * 2024.09.11 : 내 리뷰 가져오기 위한 ResponseDto
     * - boolean nextPage: 더보기 출력을 위한 다음페이지 여부
     * - List<ReviewDto>: 전달할 리뷰 목록
     */
    @Getter
    @AllArgsConstructor
    public static class ResponseReviewsDto {
        private boolean nextPage;
        private List<ReviewDto> reviews;
    }

    /**
     * 작성자: 오지수
     * 2024.09.11 : 마이페이지에 전달할 리뷰 정보
     * - Long parkinglotId: 주차장 페이지로 이동을 위한 주차장 id
     * - String parkinglotName: 주차장 이름
     * - String review: 사용자가 작성한 리뷰
     * - LocalDate reviewDate: 사용자가 리뷰를 작성한 날짜
     */
    @Getter
    public static class ReviewDto {
        private Long parkinglotId;
        private String parkinglotName;
        private String review;
        private Double rating;
        private LocalDate reviewDate;
        private Boolean modifable;

        public ReviewDto(Review review, String reviewText) {
            this.parkinglotId = review.getParkingLot().getId();
            this.parkinglotName = review.getParkingLot().getName();
            this.review = reviewText;
            this.rating = review.getRating();
            this.reviewDate = review.getCreatedAt().toLocalDate();
            this.modifable = review.getComplaint().equals(Bool.N) || review.getComplaint().equals(Bool.D);
        }
    }

    /**
     * 작성자: 오지수
     * 2024.09.11 : 마이 페이지에서 내 예약내역을 요청하기 위해 사용
     * JPA에서 LocalDateTime으로 저장되어 있으므로 LocalDateTime으로 가져옴
     * - LocalDateTime StartDate: 예약을 불러올 시작 날짜
     * - LocalDateTime endDate: 예약을 불러올 마지막날짜
     */
    @Getter
    @AllArgsConstructor
    public static class MyReservationRequestDto {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime startDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime endDate;
    }


    /**
     * 작성자: 오지수
     * 2024.09.11 : 마이페이지에 내 예약 내역을 전달하기 위해 사용
     * - boolean nextPage: 더보기 출력을 위한 다음 페이지 여부
     * - List<MyReservation>: 전달할 문의 목록
     */
    @Getter
    @AllArgsConstructor
    public static class MyReservationResponseDto {
        private boolean nextPage;
        List<MyReservation> reservations;
    }

    /**
     * 작성자: 오지수
     * 2024.09.11 : 마이페이지에 전달할 예약 정보
     * - Long reservationId: 상세 내역을 가져오기 위한 예약 id
     * - Long parkinglotId: 주차장 페이지로 이동을 위한 주차장 id
     * - String parkinglotName: 주차장 이름
     * - LocalDateTime startDate: 예약 시작 시간
     * - LocalDateTime endDate: 예약 끝 시간
     */
    @Getter
    public static class MyReservation {
        private Long reservationId;
        private Long parkinglotId;
        private String parkingLotName;
        private String startDate;
        private String endDate;
        private String carNumber;
        private String carType;
        private String status;

        public MyReservation(Reservation reservation) {
            this.reservationId = reservation.getId();
            this.parkinglotId = reservation.getParkingLot().getId();
            this.parkingLotName = reservation.getParkingLot().getName();
            this.startDate = reservation.getStartTime().toString();
            this.endDate = reservation.getEndTime().toString();
            this.carNumber = reservation.getPlateNumber();
            this.carType = reservation.getParkingSpace().getCarType().getCarTypeEnum().getKor();
            this.status = reservation.getReservationConfirmed().getString();
        }
    }

    /**
     * @Author김경민
     * @Date 2024.09.17
     * 상세 예약 페이지 DTO
     * */
    @Getter
    @AllArgsConstructor
    public static class ResponseReservationDetailsDto{
        private String reservationUuid;
        private Bool reservationConfirmed;
        private Integer totalPrice;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Bool maintenance;
        private Bool wash;
        private String plateNumber;
        private String lotName;
        private String address;
        private String tel;
    }

    /**
     * 작성자: 오지수
     * 2024.09.20 : 마이페이지에 내 문의 내역을 전달하기 위해 사용
     */
    @Getter
    @AllArgsConstructor
    public static class MyInquiryResponseDto {
        private boolean nextPage; // 더보기 출력을 위한 다음 페이지 여부
        List<MyInquiry> inquiries; //전달할 문의 목록
    }

    /**
     * 작성자: 오지수
     * 2024.09.20 : 마이페이지에 전달할 내 문의 정보
     */
    @Getter
    public static class MyInquiry {
        private Long inquiryId;
        private Long parkinglotId;
        private String parkingLotName;
        private String inquiryDate;
        private String inquiry;
        private Boolean isIfAnswer;
//        private String answer;

        public MyInquiry(Inquiry inquiry) {
            this.inquiryId = inquiry.getId();
            this.parkinglotId = inquiry.getParkingLot().getId();
            this.parkingLotName = inquiry.getParkingLot().getName();
            this.inquiryDate = inquiry.getInquiryCreatedAt().toLocalDate().toString();
            this.inquiry = inquiry.getInquiry();
//            this.answerDate = Optional.ofNullable(inquiry.getAnswer()).orElse("");
//            this.answer = Optional.ofNullable(inquiry.getAnswer()).orElse("");
            this.isIfAnswer = Optional.ofNullable(inquiry.getAnswerCreatedAt()).isPresent();
        }
    }

    /**
     * 작성자: 오지수
     * 2024-09-25: 마이페이지 문의 상세 정보에 보내는 정보
     */
//    @AllArgsConstructor
    @Getter
    public static class ResponseInquiryDto {
        private long parkinglotId;
        private String parkinglotName;
        private String inquiryDate;
        private String answerDate;
        private String inquiry;
        private String answer;

        public ResponseInquiryDto(Inquiry inquiry) {
            this.parkinglotId = inquiry.getParkingLot().getId();
            this.parkinglotName = inquiry.getParkingLot().getName();
            this.inquiryDate = inquiry.getInquiryCreatedAt().toLocalDate().toString();
            this.answerDate = inquiry.getAnswerCreatedAt() == null? "" : inquiry.getAnswerCreatedAt().toLocalDate().toString();
            this.inquiry = inquiry.getInquiry();
            this.answer = inquiry.getAnswer() == null ? "" : inquiry.getAnswer();
        }
    }
}
