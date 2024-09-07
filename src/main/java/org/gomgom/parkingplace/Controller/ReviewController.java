package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.ReviewDto;
import org.gomgom.parkingplace.Service.Review.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/*
작성자: 오지수
 */
@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/protected/parkinglots/{parkinglotId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /*
    작성자: 오지수
    리뷰 등록
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/api/protected/parkinglots/{parkinglotId}/reviews")
    public ResponseEntity<?> registerReview(@PathVariable("parkinglotId") Long parkinglotId,
                                         @RequestBody ReviewDto.ReviewRegisterRequestDto reviewDto,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {

        String result = reviewService.saveReview(userDetails.getUser(), parkinglotId, reviewDto.getNewReview());
        return ResponseEntity.ok(result);
    }

    /*
    작성자: 오지수
    리뷰 가져오기
     */
    @GetMapping("/api/parkinglots/{parkinglotId}/reviews")
    public ResponseEntity<?> getReviews(@PathVariable("parkinglotId") Long parkinglotId) {
        return ResponseEntity.ok(reviewService.getReviews(parkinglotId));
    }
}
