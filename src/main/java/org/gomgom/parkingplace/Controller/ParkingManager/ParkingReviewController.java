package org.gomgom.parkingplace.Controller.ParkingManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.ReviewDto;
import org.gomgom.parkingplace.Service.Review.ReviewService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/parking-manager/parkinglots/{parkinglotId}")
@RequiredArgsConstructor
@Log4j2
public class ParkingReviewController {
    private final ReviewService reviewService;

    @GetMapping("/reviews/protected")
    @PreAuthorize("hasRole('ROLE_PARKING_MANAGER')")
    public ResponseEntity<ReviewDto.ParkingReviewsResponseDto> getParkingReviews(@PathVariable Long parkinglotId,
                                                                                 ReviewDto.ParkingReviewsRequestDto requestDto,
                                                                                 @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Controller: 주차장 관리자 리뷰 목록 불러오기");
        return ResponseEntity.ok(reviewService.getReviewsByParking(userDetails.getUser(), parkinglotId, requestDto, pageable));
    }

    @GetMapping("/reviews/{reviewId}/protected")
    @PreAuthorize("hasRole('ROLE_PARKING_MANAGER')")
    public ResponseEntity<ReviewDto.ParkingReviewsDto> getReviewDtails(@PathVariable Long parkinglotId,
                                                @PathVariable Long reviewId,
                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Controller: 주차장 관리자 리뷰 상세 정보 불러오기");
        return ResponseEntity.ok(reviewService.getReivewDatailsByParking(userDetails.getUser(), parkinglotId, reviewId));
    }

    @PutMapping("/reviews/{reviewId}/protected")
    @PreAuthorize("hasRole('ROLE_PARKING_MANAGER')")
    public ResponseEntity<Void> complaintReview(@PathVariable Long parkinglotId,
                                                @PathVariable Long reviewId,
                                                @RequestBody ReviewDto.ParkingComplainReviewDto reviewDto,
                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Controller: 주차장 관리자 리뷰 신고");
        log.info(reviewDto.getComplaintReason());
        reviewService.complaintReviewByParking(userDetails.getUser(), parkinglotId, reviewId, reviewDto.getComplaintReason());
        return ResponseEntity.ok().build();
    }
}
