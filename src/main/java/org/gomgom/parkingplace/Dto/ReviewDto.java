package org.gomgom.parkingplace.Dto;

import lombok.Getter;
import org.gomgom.parkingplace.Entity.Review;

import java.time.LocalDate;
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
}
