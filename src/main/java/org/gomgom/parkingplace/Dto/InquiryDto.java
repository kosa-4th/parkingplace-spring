package org.gomgom.parkingplace.Dto;

import lombok.Getter;
import org.gomgom.parkingplace.Entity.Inquiry;

import java.time.LocalDate;
import java.util.List;

public class InquiryDto {

    /**
     * 문의 목록을 보낼때 사용하는 dto
     */
    @Getter
    public static class ResponseInquiriesDto {
        private final boolean nextPage;
        private final List<InquiriesDto> inquiries;

        public ResponseInquiriesDto(boolean nextPage, List<InquiriesDto> inquiries) {
            this.nextPage = nextPage;
            this.inquiries = inquiries;
        }
    }

    @Getter
    public static class InquiriesDto {
        private Long id;
        private String inquirer;
        private String inquiry;
        private String answer;
        private LocalDate inquiryDate;
        private LocalDate answerDate;

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
     * 문의하기 RequestDto
     */
    @Getter
    public static class RequestInquiriesDto {
        private String inquiry;
    }
}
