package org.gomgom.parkingplace.Service.Review;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gomgom.parkingplace.Dto.ReviewDto;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.Review;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Exception.CustomExceptions;
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

    /**
     * 작성자: 오지수
     * 2024.09.08 : 주차장 상세페이지에 작성된 리뷰 목록
     * @param parkingplotId 주차장 id
     * @param pageable 페이지 정보
     * @return
     */
    @Override
    public ReviewDto.ReviewsResponseDto getReviews(Long parkingplotId, Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findByParkingLotId(parkingplotId, pageable);

        return new ReviewDto.ReviewsResponseDto(reviewPage.hasNext(),
                reviewPage.stream().map(ReviewDto.ReviewsDto::new).toList());
    }

    /**
     * 작성자: 오지수
     * 2024.09.07 : 주차장 페이지에 사용자가 리뷰 작성
     * @param user 사용자 정보
     * @param parkinglotId 주차장 id
     * @param review 작성한 리뷰
     * @return
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

    /**
     * 작성자: 오지수
     * 2024.09.08 : 리뷰삭제
     * @param userId 사용자 id
     * @param parkinglotId 주차장 id
     * @param reviewId 삭제할 리뷰 id
     */
    @Override
    @Transactional
    public void deleteReview(Long userId, Long parkinglotId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
        
        if (!review.getParkingLot().getId().equals(parkinglotId)) {
            log.info("Service: 주차장 리뷰 삭제, 리뷰의 parkinglot 정보와 parkinglotId param 불일치");
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        if (!review.getUser().getId().equals(userId)) {
            log.info("Service: 주차장 리뷰 삭제, 리뷰 user 정보와 userId 불일치");
            throw new IllegalArgumentException("삭제 권한이 없는 리뷰입니다.");
        }
        log.info("Service: 주차장 삭제 완료");
        reviewRepository.delete(review);
    }

    /**
     * 작성자: 오지수
     * 2024.09.08 : 리뷰 수정
     * @param userId 사용자id
     * @param parkinglotId 주차장 id
     * @param reviewId 리뷰 id
     * @param newReview 수정할 리뷰
     */
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

    @Override
    public ReviewDto.ParkingReviewsResponseDto getReviewsByParking(User user, Long parkinglotId, ReviewDto.ParkingReviewsRequestDto dto, Pageable pageable) {
        log.info("Service: 주차장 관리자 리뷰 목록 가져오기");
        ParkingLot parkingLot = parkingLotRepository.findById(parkinglotId)
                .orElseThrow(() -> new CustomExceptions.ValidationException("존재하지 않는 주차장입니다."));

        if (!parkingLot.getUser().getId().equals(user.getId())) {
            log.info("사용자 불일치");
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        Page<Review> reviewPage = reviewRepository.findReviewsByParkingLotAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(parkingLot, dto.getFrom(), dto.getTo().plusDays(1), pageable);

        return new ReviewDto.ParkingReviewsResponseDto(reviewPage.getTotalPages(), reviewPage.getNumber(),
                reviewPage.stream().map(ReviewDto.ParkingReviewsDto::new).toList());
    }
}
