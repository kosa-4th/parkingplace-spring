package org.gomgom.parkingplace.Service.Review;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gomgom.parkingplace.Dto.ReviewDto;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.Review;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Repository.ParkingLotRepository;
import org.gomgom.parkingplace.Repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final ParkingLotRepository parkingLotRepository;


    @Override
    public ReviewDto.ReviewsResponseDto getReviews(Long parkingplotId, Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findByParkingLotId(parkingplotId, pageable);

        return new ReviewDto.ReviewsResponseDto(reviewPage.hasNext(),
                reviewPage.stream().map(ReviewDto.ReviewsDto::new).toList());
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

    @Override
    @Transactional
    public void deleteReview(Long userId, Long parkinglotId, Long reviewId) {
        // 유효성 검사 필요
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        if (!review.getParkingLot().getId().equals(parkinglotId)) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("삭제 권한이 없는 리뷰입니다.");
        }
        reviewRepository.delete(review);
    }

    @Override
    @Transactional
    public void modifyReview(Long userId, Long parkinglotId, Long reviewId, String newReview) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        if (!review.getParkingLot().getId().equals(parkinglotId)) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("수정 권한이 없는 리뷰입니다.");
        }
        // 로그 추가
        log.info("리뷰 {} 수정 by 사용자 {}: {}", reviewId, userId, newReview);
        review.setReview(newReview);
    }
}
