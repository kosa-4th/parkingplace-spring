package org.gomgom.parkingplace.Service.myPage;

import org.gomgom.parkingplace.Dto.MyPageDto;
import org.gomgom.parkingplace.Entity.User;
import org.springframework.data.domain.Pageable;


public interface MyPageService {

    // 내 리뷰 목록 가져오기
    MyPageDto.ResponseReviewsDto getMyReviews(User user, Pageable pageable);

    // 내 예약 목록 가져오기
    MyPageDto.MyReservationResponseDto getMyReservations(User user, MyPageDto.MyReservationRequestDto dateDto, Pageable pageable);

    /**
     * @Date 2024.09.17
     * @Author 김경민
     * 예약상세 가져오기
     * */
    MyPageDto.ResponseReservationDetailsDto getMyReservationDetails(Long reservationId, Long userId);

    // 내 문의 목록 가져오기
    MyPageDto.MyInquiryResponseDto getMyInquiries(User user, Pageable pageable);

}
