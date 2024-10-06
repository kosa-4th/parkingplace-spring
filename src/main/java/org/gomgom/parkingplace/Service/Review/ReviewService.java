package org.gomgom.parkingplace.Service.Review;

import org.gomgom.parkingplace.Dto.ReviewDto;
import org.gomgom.parkingplace.Entity.User;
import org.springframework.data.domain.Pageable;


public interface ReviewService {
    ReviewDto.ReviewsResponseDto getReviews(Long parkingplotId, Pageable pageable);

    String saveReview(User user, Long parkinglotId, ReviewDto.ReviewRequestDto reviewDto);

    void deleteReview(Long userId, Long parkinglotId, Long reviewId);

    void modifyReview(Long userId, Long parkinglotId, Long reviewId, String review);

    ReviewDto.ParkingReviewsResponseDto getReviewsByParking(User user, Long parkinglotId, ReviewDto.ParkingReviewsRequestDto dto, Pageable pageable);

    ReviewDto.ParkingReviewsDto getReivewDatailsByParking(User user, Long parkinglotId, Long reviewId);

    void complaintReviewByParking(User user, Long parkinglotId, Long reviewId, String complaintReason);

    ReviewDto.SystemReviewsResponseDto getReviewsBySystem(ReviewDto.RequestSystemReviewDto requestDto, Pageable pageable);

    ReviewDto.SystemReviewDetailsResponseDto getReviewDetails(Long reviewId);

    void completeComplaintReview(Long reviewId);

    void rejectComplaintReview(Long reviewId);
}
