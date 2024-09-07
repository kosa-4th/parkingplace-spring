package org.gomgom.parkingplace.Dto;

import lombok.Builder;
import lombok.Getter;
import org.gomgom.parkingplace.Entity.Review;

import java.time.LocalDate;

public class ReviewDto {

    /*
    작성자: 오지수
     */
    @Getter
    public static class ReviewRegisterRequestDto {
        private String newReview;
    }

    /*
    작성자: 오지수
     */
    @Getter
    public static class ReviewsResponseDto {
        private String reviewer;
        private LocalDate reviewDate;
        private String review;

        public ReviewsResponseDto(Review review) {
            this.review = review.getReview();
            this.reviewDate = review.getCreatedAt().toLocalDate();
            this.reviewer = review.getUser().getName();
        }
    }
}
