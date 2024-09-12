package org.gomgom.parkingplace.Service.myPage;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.MyPageDto;
import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.Entity.Review;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Repository.ReservationRepository;
import org.gomgom.parkingplace.Repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 작성자: 오지수
     * 2024.09.11 : 마이페이지에서 내가 작성한 리뷰 목록
     * @param user
     * @param pageable
     * @return 리뷰 목록
     */
    @Override
    public MyPageDto.ResponseReviewsDto getMyReviews(User user, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByUser(user, pageable);
        return new MyPageDto.ResponseReviewsDto(reviews.hasNext(),
                reviews.stream().map(MyPageDto.ReviewDto::new).toList());
    }

    /**
     * 작성자: 오지수
     * 2024.09.11 :  마이페이지에서 내가 예약한 내역
     * @param user
     * @param dateDto 예약을 확인할 날짜 간격
     * @param pageable
     * @return
     */
    @Override
    public MyPageDto.MyReservationResponseDto getMyReservations(User user, MyPageDto.MyReservationRequestDto dateDto, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findByUserAndStartTimeGreaterThanEqualAndEndTimeLessThan(user, dateDto.getStartDate(), dateDto.getEndDate().plusDays(1), pageable);
        return new MyPageDto.MyReservationResponseDto(reservations.hasNext(),
                reservations.stream().map(MyPageDto.MyReservation::new).toList());
    }
}
