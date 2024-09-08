package org.gomgom.parkingplace.Dto;

import lombok.Getter;
import org.gomgom.parkingplace.Entity.Review;

import java.time.LocalDate;
import java.util.List;

public class ReviewDto {

    /*
    작성자: 오지수
     */
    @Getter
    public static class ReviewRequestDto {
        private String newReview;
    }

    /*
    작성자: 오지수
     */
    @Getter
    public static class ReviewsResponseDto {
        private boolean nextPage;
        private List<ReviewsDto> reviews;

        public ReviewsResponseDto(boolean nextPage, List<ReviewsDto> reviews) {
            this.nextPage = nextPage;
            this.reviews = reviews;
        }
    }

    public static class ReviewsDto {
        public Long id;
        public String reviewer;
        public LocalDate reviewDate;
        public String review;
        public String email;

        public ReviewsDto(Review review) {
            this.id = review.getId();
            this.review = review.getReview();
            this.reviewDate = review.getCreatedAt().toLocalDate();
            this.reviewer = review.getUser().getName();
            this.email = review.getUser().getEmail();
        }
    }
}
