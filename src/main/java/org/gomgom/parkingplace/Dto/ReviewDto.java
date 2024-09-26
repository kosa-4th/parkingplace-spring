package org.gomgom.parkingplace.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gomgom.parkingplace.Entity.Review;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewDto {

    /**
     * 작성자: 오지수
     * 2024.09.07 : 사용자가 새로 작성, 수정한 리뷰 내용
     */
    @Getter
    public static class ReviewRequestDto {
        private String newReview; //리뷰 내용
    }

    /**
     * 작성자: 오지수
     * 2024.09.07 : 주차장 상세 리뷰 페이지에 전달할 리뷰 목록
     */
    @Getter
    public static class ReviewsResponseDto {
        private boolean nextPage; // 더보기 출력을 위한 다음 페이지 여부
        private List<ReviewsDto> reviews; // 전달할 리뷰 목록

        public ReviewsResponseDto(boolean nextPage, List<ReviewsDto> reviews) {
            this.nextPage = nextPage;
            this.reviews = reviews;
        }
    }

    /**
     * 작성자: 오지수
     * 2024.09.07 : 주차장 리뷰 페이지에 전달할 리뷰 정보
     */
    public static class ReviewsDto {
        public Long id; // 리뷰 id
        public String reviewer; // 리뷰 작성자 이름
        public LocalDate reviewDate; // 리뷰 작성한 시간
        public String review; // 리뷰 내용
        public String email; // 리뷰 작성자 이메일

        public ReviewsDto(Review review) {
            this.id = review.getId();
            this.review = review.getReview();
            this.reviewDate = review.getCreatedAt().toLocalDate();
            this.reviewer = review.getUser().getName();
            this.email = review.getUser().getEmail();
        }
    }

    /**
     * 작성자: 오지수
     * 2024.09.21: 주차장 관리 페이지에서 리뷰 목록을 가져옴
     */
    @Getter
    @AllArgsConstructor
    public static class ParkingReviewsRequestDto {
//        private Long parkinglotId;
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
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

    @AllArgsConstructor
    @Getter
    public static class ParkingReviewsResponseDto {
        private int totalPages;
        private int currentPage;
        List<ParkingReviewsDto> parkingReviews;
    }

    @Getter
    public static class ParkingReviewsDto {
        public Long reviewId;
        public String reviewer;
        public String review;
        public String reviewDate;

        public ParkingReviewsDto(Review review) {
            this.reviewId = review.getId();
            this.reviewer = review.getUser().getName();
            this.review = review.getReview();
            this.reviewDate = review.getCreatedAt().toLocalDate().toString();
        }
    }
}
