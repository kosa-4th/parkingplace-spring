package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.MyPageDto;
import org.gomgom.parkingplace.Repository.ReservationRepository;
import org.gomgom.parkingplace.Service.myPage.MyPageService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Log4j2
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
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<MyPageDto.ResponseReviewsDto> getMyReviews(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
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
     */
    @GetMapping("/reservations/protected")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<MyPageDto.MyReservationResponseDto> getMyReservations(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                                MyPageDto.MyReservationRequestDto requestDto,
                                                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
//        System.out.println(userDetails.getUsername());
//        System.out.println(requestDto.getStartDate());
//        System.out.println(requestDto.getEndDate());
        log.info("Controller: 마이페이지 / 예약 목록 가져오기");
        return ResponseEntity.ok(myPageService.getMyReservations(userDetails.getUser(), requestDto, pageable));
    }

    /**
     * @Author 김경민
     * @Date 2024.09.17
     *
     * 사용자 예약 상세 페이지 데이터 가져오기
     * */
    @GetMapping("/reservationsDetails/{reservationId}/protected")
    public ResponseEntity<MyPageDto.ResponseReservationDetailsDto> getMyReservationDetails(@PathVariable Long reservationId,
                                                                                         @AuthenticationPrincipal CustomUserDetails userDetails){
        System.out.println("테스트");
        Long userId = userDetails.getUser().getId();
        try {
            // 서비스 호출
            MyPageDto.ResponseReservationDetailsDto reservationDetails = myPageService.getMyReservationDetails(reservationId, userId);
            return ResponseEntity.ok(reservationDetails);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    /**
     * 작성자: 오지수
     * 2024.09.20 : 내 문의 목록
     * @param pageable / page, size
     * @param userDetails / 사용자 정보
     *
     * @return 다음 페이지 여부와 예약 정보 / MyPageDto.MyInquiryResponseDto
     * ---------------------------------
     */
    @GetMapping("/inquiries/protected")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<MyPageDto.MyInquiryResponseDto> getMyInquiries(@PageableDefault(sort = "inquiryCreatedAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Controller: 마이페이지 / 문의 목록 가져오기");
        return ResponseEntity.ok(myPageService.getMyInquiries(userDetails.getUser(), pageable));
    }

    @GetMapping(value = "/inquiries/{inquiryId}/protected")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<MyPageDto.ResponseInquiryDto> getMyInquiry(@PathVariable Long inquiryId,
                                                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Controller: 마이페이지 / 상세 문의 가져오기");
        return ResponseEntity.ok(myPageService.getInquiryDetails(userDetails.getUser(), inquiryId));
    }
}
