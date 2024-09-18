package org.gomgom.parkingplace.Service.myPage;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.MyPageDto;
import org.gomgom.parkingplace.Dto.ReservationDto;
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
public class MyPageServiceImpl implements MyPageService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 작성자: 오지수
     * 2024.09.11 : 마이페이지에서 내가 작성한 리뷰 목록
     *
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
     *
     * @param user
     * @param dateDto  예약을 확인할 날짜 간격
     * @param pageable
     * @return
     */
    @Override
    public MyPageDto.MyReservationResponseDto getMyReservations(User user, MyPageDto.MyReservationRequestDto dateDto, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findByUserAndStartTimeGreaterThanEqualAndEndTimeLessThan(user, dateDto.getStartDate(), dateDto.getEndDate().plusDays(1), pageable);
        return new MyPageDto.MyReservationResponseDto(reservations.hasNext(),
                reservations.stream().map(MyPageDto.MyReservation::new).toList());
    }

    /**
     * @Author 김경민
     * @Date 2024.09.17
     * <p>
     * 예약정보 상세 가져오기
     */
    @Override
    public MyPageDto.ResponseReservationDetailsDto getMyReservationDetails(Long reservationId, Long userId) {

        Reservation reservation = reservationRepository.findReservationByIdAndUserId(reservationId, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다."));

        return new MyPageDto.ResponseReservationDetailsDto(
                reservation.getReservationUuid(),
                reservation.getReservationConfirmed(),
                reservation.getTotalPrice(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getMaintenance(),
                reservation.getWash(),
                reservation.getPlateNumber(),
                reservation.getParkingLot().getName(),
                reservation.getParkingLot().getAddress(),
                reservation.getParkingLot().getTel()
                );
    }
}
