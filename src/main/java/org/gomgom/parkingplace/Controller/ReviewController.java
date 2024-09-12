package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.ReviewDto;
import org.gomgom.parkingplace.Service.Review.ReviewService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parkinglots/{parkinglotId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 작성자: 오지수
     * 2024.09.07 : 주차장 상세페이지에서 사용자가 리뷰 작성
     * @param parkinglotId / 주차장 id
     * @param reviewDto / 작성한 리뷰
     * @param userDetails / 사용자 정보
     * @return /
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/protected")
    public ResponseEntity<?> registerReview(@PathVariable("parkinglotId") Long parkinglotId,
                                         @RequestBody ReviewDto.ReviewRequestDto reviewDto,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {

        String result = reviewService.saveReview(userDetails.getUser(), parkinglotId, reviewDto.getNewReview());
        return ResponseEntity.ok(result);
    }

    /**
     * 작성자: 오지수
     * 2024.09.07 : 주차장 상세페이지에서 리뷰 목록 불러오기
     * @param parkinglotId / 주차장 id
     * @param pageable / page, size
     * @return 다음페이지 여부, 리뷰 정보
     * - 리뷰 id, 리뷰 작성자 이름, 리뷰 작성 날짜, 리뷰, 리뷰 작성자 이메일(앞단에서 작성자 여부 확인을 위해)
     */
    @GetMapping()
    public ResponseEntity<?> getReviews(@PathVariable("parkinglotId") Long parkinglotId,
                                        Pageable pageable) {
        return ResponseEntity.ok(reviewService.getReviews(parkinglotId, pageable));
    }

    /**
     * 작성자: 오지수
     * 2024.09.07 : 주자창에 작성된 리뷰 삭제하기
     * @param parkinglotId / 주차장 id
     * @param reviewId / 리뷰 id
     * @param userDetails / 사용자 정보
     * @return /
     */
    @DeleteMapping("/{reviewId}/protected")
    public ResponseEntity<?> deleteReview(@PathVariable("parkinglotId") Long parkinglotId,
                                          @PathVariable("reviewId") Long reviewId,
                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            reviewService.deleteReview(userDetails.getUser().getId(), parkinglotId, reviewId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 작성자: 오지수
     * 2024.09.07 : 주차장에 작성된 리뷰 수정하기
     * @param parkinglotId / 주차장 id
     * @param reviewId / 리뷰 id
     * @param reviewDto / 수정된 리뷰 내용
     * @param userDetails / 사용자 정보
     * @return /
     */
    @PutMapping("/{reviewId}/protected")
    public ResponseEntity<?> modifyReview(@PathVariable("parkinglotId") Long parkinglotId,
                                          @PathVariable("reviewId") Long reviewId,
                                          @RequestBody ReviewDto.ReviewRequestDto reviewDto,
                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            reviewService.modifyReview(userDetails.getUser().getId(), parkinglotId,reviewId, reviewDto.getNewReview());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
