package org.gomgom.parkingplace.Dto;

import lombok.Getter;
import org.gomgom.parkingplace.Entity.Inquiry;

import java.time.LocalDate;
import java.util.List;

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
}
