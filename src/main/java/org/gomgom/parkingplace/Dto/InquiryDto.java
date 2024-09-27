package org.gomgom.parkingplace.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gomgom.parkingplace.Entity.Inquiry;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class InquiryDto {

    /**
     * 작성자: 오지수
     * 2024.09.11 : 주차장 상세페이지에 전달할 문의 정보
     */
    @Getter
    public static class ResponseInquiriesDto {
        private final boolean nextPage; // 더보기 출력을 위한 다음 페이지 여부
        private final List<InquiriesDto> inquiries; // 문의 목록

        public ResponseInquiriesDto(boolean nextPage, List<InquiriesDto> inquiries) {
            this.nextPage = nextPage;
            this.inquiries = inquiries;
        }
    }

    /**
     * 작성자: 오지수
     * 2024.09.11 : 문의 정보
     */
    @Getter
    public static class InquiriesDto {
        private Long id; // 문의 id
        private String inquirer; // 문의 작성자 이름
        private String iqnuirerEmail;
        private String inquiry; // 문의 내용
        private String answer; // 답변
        private boolean isSecret;
        private LocalDate inquiryDate; // 문의 날짜
        private LocalDate answerDate; // 답변 날짜

        public InquiriesDto(Inquiry inquiry) {
            this.id = inquiry.getId();
            this.inquirer = inquiry.getUser().getName();
            this.iqnuirerEmail = inquiry.getUser().getEmail();
            this.inquiry = inquiry.getInquiry();
            this.answer = inquiry.getAnswer() == null ? "" : inquiry.getAnswer();
            this.isSecret = inquiry.getIsSecret().equals(Bool.Y);
            this.inquiryDate = inquiry.getInquiryCreatedAt().toLocalDate();
            this.answerDate = inquiry.getAnswerCreatedAt() == null ? null : inquiry.getAnswerCreatedAt().toLocalDate();
        }
    }

    /**
     * 작성자: 오지수
     * 2024.09.12 : 문의 요청
     * 새로 작성할 거 받아옴
     */
    @Getter
    public static class RequestInquiriesDto {
        private Boolean secret;
        private String inquiry; // 문의ㅣ
    }

    @Getter
    public static class ResponseInquiryDto {
        private Boolean isSecret;
        private String inquiry; // 문의

        public ResponseInquiryDto(Inquiry inquiry) {
            this.isSecret = inquiry.getIsSecret().equals(Bool.Y);
            this.inquiry = inquiry.getInquiry();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestInquiryModifyDto {
        private Boolean isSecret;
        private String newInquiry;
    }

    /**
     * 작성자: 오지수
     * 2024.09.21: 주차장 관리자 페이지 / 문의 목록 요청
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class ParkingInquiryRequestDto {
//        private Long parkinglotId;
        private String actionType;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate from;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate to;

        public LocalDateTime getFrom() {
            return from != null ? from.atStartOfDay() : LocalDateTime.of(2000, 1, 1, 0, 0);
        }

        public LocalDateTime getTo() {
            return to != null ? to.atStartOfDay() : LocalDateTime.now().plusDays(30);
        }

    }

    /**
     * 작성자: 오지수
     * 2024.09.21: 주차장 관리자 페이지 / 문의 반환
     */
    @AllArgsConstructor
    @Getter
    public static class ParkingInquiryResponseDto {
        private int totalPages;
        private int currentPage;
        List<ParkingInquiryDto> inquiries;
    }

    public static class ParkingInquiryDto {
        public Long inquiryId;
        public String inquiry;
        public String inquirer;
        public String inquiryDate;
        public boolean isIfAnswer;

        public ParkingInquiryDto(Inquiry inquiry) {
            this.inquiryId = inquiry.getId();
            this.inquiry = inquiry.getInquiry();
            this.inquirer = inquiry.getUser().getName();
            this.inquiryDate = inquiry.getInquiryCreatedAt().toLocalDate().toString();
            this.isIfAnswer = Optional.ofNullable(inquiry.getAnswerCreatedAt()).isPresent();
        }
    }

    /**
     * 작성자: 오지수
     * 문의 1개 디테일 정보
     */
    @Getter
    public static class ParkingInquiryDetailDto {
        public Long inquiryId;
        public String inquiry;
        public String inquirer;
        public String inquiryDate;
        public String inquiryUpdateDate;
        public String answer;
        public String answerDate;
        public String answerUpdateDate;

        public ParkingInquiryDetailDto(Inquiry inquiry) {
            this.inquiryId = inquiry.getId();
            this.inquiry = inquiry.getInquiry();
            this.inquirer = inquiry.getUser().getName();
            this.inquiryDate = inquiry.getInquiryCreatedAt().toLocalDate().toString();
            this.inquiryUpdateDate = inquiry.getInquiryUpdatedAt() != null ? inquiry.getInquiryUpdatedAt().toLocalDate().toString() : "";
            this.answer = inquiry.getAnswer() == null ? "" : inquiry.getAnswer();
            this.answerDate = inquiry.getAnswerCreatedAt() != null ? inquiry.getAnswerCreatedAt().toLocalDate().toString() : "";
            this.answerUpdateDate = inquiry.getAnswerUpdatedAt() != null ? inquiry.getAnswerUpdatedAt().toLocalDate().toString() : "";
        }
    }

    /**\
     * 작성자: 오지수
     * 주차장 관리자 페이지 / 문의 등록, 수정
     */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ParkingInquiryAnswerRequest {
        private String answer;
    }
}
