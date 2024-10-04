package org.gomgom.parkingplace.Service.myPage;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gomgom.parkingplace.Dto.MyPageDto;
import org.gomgom.parkingplace.Dto.ReservationDto;
import org.gomgom.parkingplace.Dto.ReviewDto;
import org.gomgom.parkingplace.Entity.Inquiry;
import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.Entity.Review;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Exception.CustomExceptions;
import org.gomgom.parkingplace.Repository.InquiryRepository;
import org.gomgom.parkingplace.Repository.ReservationRepository;
import org.gomgom.parkingplace.Repository.ReviewRepository;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MyPageServiceImpl implements MyPageService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final InquiryRepository inquiryRepository;

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
        Page<Review> reviewPage = reviewRepository.findByUser(user, pageable);

        List<MyPageDto.ReviewDto> reviews = reviewPage.stream()
                .map(review -> {
                    String reviewText;
                    if (review.getComplaint().equals(Bool.C)) {
                        reviewText = "부적절한 내용으로 신고 요청된 리뷰입니다.";
                    } else if (review.getComplaint().equals(Bool.Y)) {
                        reviewText = "관리자에 의해 삭제된 리뷰입니다.";
                    } else {
                        reviewText = review.getReview();
                    }
                    return new MyPageDto.ReviewDto(review, reviewText);
                }).toList();
        return new MyPageDto.ResponseReviewsDto(reviewPage.hasNext(), reviews);
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

    /**
     * 작성자: 오지수
     * 2024.09.20 : 마이페이지에서 내가 작성한 문의 목록
     * @param user
     * @param pageable
     * @return
     */
    @Override
    public MyPageDto.MyInquiryResponseDto getMyInquiries(User user, Pageable pageable) {
        Page<Inquiry> inquiries = inquiryRepository.findByUser(user, pageable);

        return new MyPageDto.MyInquiryResponseDto(inquiries.hasNext(),
                inquiries.stream().map(MyPageDto.MyInquiry::new).toList());
    }

    /**
     * 작성자: 오지수
     * 2024-09-25: 마이페이지 문의 상세 목록
     * @param inquiryId
     * @return
     */
    @Override
    public MyPageDto.ResponseInquiryDto getInquiryDetails(User user, Long inquiryId) {
        log.info("myPageService: 상세 문의 요청");
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CustomExceptions.ValidationException("존재하지 않는 문의 정보입니다."));

        if (!inquiry.getUser().getId().equals(user.getId())) {
            log.info("error: 작성 유저 불일치");
            throw new CustomExceptions.ValidationException("유효하지 않은 접근입니다.");
        }

        return new MyPageDto.ResponseInquiryDto(inquiry);
    }
}
