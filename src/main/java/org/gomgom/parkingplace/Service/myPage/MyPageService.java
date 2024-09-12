package org.gomgom.parkingplace.Service.myPage;

import org.gomgom.parkingplace.Dto.MyPageDto;
import org.gomgom.parkingplace.Entity.User;
import org.springframework.data.domain.Pageable;


public interface MyPageService {

    // 내 리뷰 목록 가져오기
    MyPageDto.ResponseReviewsDto getMyReviews(User user, Pageable pageable);

    // 내 예약 목록 가져오기
    MyPageDto.MyReservationResponseDto getMyReservations(User user, MyPageDto.MyReservationRequestDto dateDto, Pageable pageable);
}
