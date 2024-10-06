package org.gomgom.parkingplace.Controller.SystemManager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.ReviewDto;
import org.gomgom.parkingplace.Service.Review.ReviewService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/system-manager/reviews")
@Log4j2
public class SystemManagerReviewController {

    private final ReviewService reviewService;

    @GetMapping("/protected")
    @PreAuthorize("hasRole('ROLE_SYSTEM_MANAGER')")
    public ResponseEntity<ReviewDto.SystemReviewsResponseDto> getSystemManagerReviews(Pageable pageable,
                                                                    @ModelAttribute ReviewDto.RequestSystemReviewDto requestDto,
                                                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Controller: SystemManger get Complaint Reviews");
        System.out.println(requestDto.getActionType());
        System.out.println(requestDto.getFrom() + " " + requestDto.getTo());
        return ResponseEntity.ok(reviewService.getReviewsBySystem(requestDto, pageable));
    }

    @GetMapping("/{reviewId}/protected")
    @PreAuthorize("hasRole('ROLE_SYSTEM_MANAGER')")
    public ResponseEntity<ReviewDto.SystemReviewDetailsResponseDto> getSystemManagerReviewDetails(@PathVariable Long reviewId,
                                                                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Controller: SystemManger get Complaint Review Details");
        return ResponseEntity.ok(reviewService.getReviewDetails(reviewId));
    }

    @DeleteMapping("/{reviewId}/protected")
    @PreAuthorize("hasRole('ROLE_SYSTEM_MANAGER')")
    public ResponseEntity<Void> completeComplaintReview(@PathVariable Long reviewId,
                                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Controller: SystemManger complete Complaint Review");
        reviewService.completeComplaintReview(reviewId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{reviewId}/protected")
    @PreAuthorize("hasRole('ROLE_SYSTEM_MANAGER')")
    public ResponseEntity<Void> rejectComplaintReview(@PathVariable Long reviewId,
                                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Controller: SystemManger reject Complaint Review");
        reviewService.rejectComplaintReview(reviewId);
        return ResponseEntity.ok().build();
    }

}
