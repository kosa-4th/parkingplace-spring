package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.MyPageDto;
import org.gomgom.parkingplace.Repository.ReservationRepository;
import org.gomgom.parkingplace.Service.myPage.MyPageService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MyPageController {
    private final MyPageService myPageService;
    private final ReservationRepository reservationRepository;

    /**
     * 작성자: 오지수
     * 2024.09.11 : 내가 작성한 리뷰 목록
     * @param pageable / page, size
     * @param userDetails /사용자 정보
     *
     * @return 다음 페이지 여부와 리뷰 정보 / MyPageDto.ResponseReviewsDto
     * ---------------------------------
     */
    @GetMapping("/reviews/protected")
    public ResponseEntity<MyPageDto.ResponseReviewsDto> getMyReviews(Pageable pageable,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(myPageService.getMyReviews(userDetails.getUser(),pageable));
    }

    /**
     * 작성자: 오지수
     * 2024.09.11 : 내 예약 목록
     * @param pageable / page, size
     * @param requestDto / 예약 내역을 볼 startDate, endDate
     * @param userDetails / 사용자 정보
     *
     * @return 다음 페이지 여부와 예약 정보 / MyPageDto.MyReservationResponseDt
     * ---------------------------------
     * 데이터 없어서 테스트 안해봄 ㅎ, 테스트 필요
     */
    @PostMapping("/reservations/protected")
    public ResponseEntity<MyPageDto.MyReservationResponseDto> getMyReservations(Pageable pageable,
                                                                                @RequestBody MyPageDto.MyReservationRequestDto requestDto,
                                                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println(userDetails.getUsername());
        System.out.println(requestDto.getStartDate());
        System.out.println(requestDto.getEndDate());
        return ResponseEntity.ok(myPageService.getMyReservations(userDetails.getUser(), requestDto, pageable));
    }
}