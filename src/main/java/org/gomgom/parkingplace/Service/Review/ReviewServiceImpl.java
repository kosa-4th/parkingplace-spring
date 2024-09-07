package org.gomgom.parkingplace.Service.Review;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.ReviewDto;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.Review;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Repository.ParkingLotRepository;
import org.gomgom.parkingplace.Repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final ParkingLotRepository parkingLotRepository;

    @Override
    public List<ReviewDto.ReviewsResponseDto> getReviews(Long parkingLotId) {
        return reviewRepository.findByParkingLotId(parkingLotId)
                .stream()
                .map(ReviewDto.ReviewsResponseDto::new
                )
                .collect(Collectors.toList());
    }

    /*
        작성자: 오지수
         */
    @Override
    public String saveReview(User user, Long parkinglotId, String review) {
        // 주차장 정보
        ParkingLot parkingLot = parkingLotRepository.findById(parkinglotId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주차장을 찾을 수 없습니다."));

        // 리뷰 생성 및 저장
        Review reviewEntity = Review.builder()
                .user(user)
                .parkingLot(parkingLot)
                .review(review)
                .build();
        reviewRepository.save(reviewEntity);

        return "리뷰가 성공적으로 등록되었습니다.";
    }
}
