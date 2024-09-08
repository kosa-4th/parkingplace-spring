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


/*
작성자: 오지수
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parkinglots/{parkinglotId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /*
    작성자: 오지수
    리뷰 등록
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/protected")
    public ResponseEntity<?> registerReview(@PathVariable("parkinglotId") Long parkinglotId,
                                         @RequestBody ReviewDto.ReviewRequestDto reviewDto,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {

        String result = reviewService.saveReview(userDetails.getUser(), parkinglotId, reviewDto.getNewReview());
        return ResponseEntity.ok(result);
    }

    /*
    작성자: 오지수
    리뷰 가져오기
     */
    @GetMapping()
    public ResponseEntity<?> getReviews(@PathVariable("parkinglotId") Long parkinglotId,
                                        Pageable pageable) {
        return ResponseEntity.ok(reviewService.getReviews(parkinglotId, pageable));
    }

    /*
    리뷰 삭제하기
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

    /*
    리뷰 수정하기
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
