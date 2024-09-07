package org.gomgom.parkingplace.Service.Review;

import org.gomgom.parkingplace.Dto.ReviewDto;
import org.gomgom.parkingplace.Entity.User;

import java.util.List;

public interface ReviewService {
    List<ReviewDto.ReviewsResponseDto> getReviews(Long parkingplaceId);

    String saveReview(User user, Long parkinglotId, String review);
}
