package org.gomgom.parkingplace.Controller.ParkingManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.ReviewDto;
import org.gomgom.parkingplace.Service.Review.ReviewService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/parking-manager")
@RequiredArgsConstructor
@Log4j2
public class ParkingReviewController {
    private final ReviewService reviewService;

    @GetMapping("/reviews/protected")
    public ResponseEntity<ReviewDto.ParkingReviewsResponseDto> getParkingReviews(ReviewDto.ParkingReviewsRequestDto requestDto,
                                                                                 Pageable pageable,
                                                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Controller: 주차장 관리자 리뷰 목록 불러오기");
        System.out.println();
        System.out.println(pageable.getPageNumber() + " " + pageable.getPageSize());
        return ResponseEntity.ok(reviewService.getReviewsByParking(userDetails.getUser(), requestDto, pageable));
    }
}
