package org.gomgom.parkingplace.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gomgom.parkingplace.Entity.Inquiry;
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
        private String inquiry; // 문의 내용
        private String answer; // 답변
        private LocalDate inquiryDate; // 문의 날짜
        private LocalDate answerDate; // 답변 날짜

        public InquiriesDto(Inquiry inquiry) {
            this.id = inquiry.getId();
            this.inquirer = inquiry.getUser().getName();
            this.inquiry = inquiry.getInquiry();
            this.answer = inquiry.getAnswer() == null ? "" : inquiry.getAnswer();
            this.inquiryDate = inquiry.getInquiryCreatedAt().toLocalDate();
            this.answerDate = inquiry.getAnswerCreatedAt() == null ? null : inquiry.getAnswerCreatedAt().toLocalDate();
        }
    }

    /**
     * 작성자: 오지수
     * 2024.09.12 : 문의 요청
     */
    @Getter
    public static class RequestInquiriesDto {
        private String inquiry; // 문의
    }

    @Getter
    @AllArgsConstructor
    public static class RequestInquiryModifyDto {
        private Long inquiryId;
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
        private LocalDateTime from;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDateTime to;

        public LocalDateTime getFrom() {
            return from != null ? from : LocalDateTime.of(2000, 1, 1, 0, 0);
        }

        public LocalDateTime getTo() {
            return to != null ? to : LocalDateTime.now().plusDays(30);
        }

    }

    /**
     * 작성자: 오지수
     * 2024.09.21: 주차장 관리자 페이지 / 문의 반환
     */
    @AllArgsConstructor
    @Getter
    public static class ParkingInquiryResponseDto {
        private boolean nextPage;
        private int pageNum;
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
        public String answerDate;
        public String answerUpdateDate;

        public ParkingInquiryDetailDto(Inquiry inquiry) {
            this.inquiryId = inquiry.getId();
            this.inquiry = inquiry.getInquiry();
            this.inquirer = inquiry.getUser().getName();
            this.inquiryDate = inquiry.getInquiryCreatedAt().toLocalDate().toString();
            this.inquiryUpdateDate = inquiry.getInquiryUpdatedAt().toLocalDate().toString();
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
