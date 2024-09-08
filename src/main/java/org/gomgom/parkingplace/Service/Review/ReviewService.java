package org.gomgom.parkingplace.Service.Review;

import org.gomgom.parkingplace.Dto.ReviewDto;
import org.gomgom.parkingplace.Entity.User;
import org.springframework.data.domain.Pageable;


public interface ReviewService {
    ReviewDto.ReviewsResponseDto getReviews(Long parkingplotId, Pageable pageable);

    String saveReview(User user, Long parkinglotId, String review);

    void deleteReview(Long userId, Long parkinglotId, Long reviewId);

    void modifyReview(Long userId, Long parkinglotId, Long reviewId, String review);
}
