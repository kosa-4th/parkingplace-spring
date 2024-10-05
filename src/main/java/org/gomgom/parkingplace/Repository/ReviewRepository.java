package org.gomgom.parkingplace.Repository;

import jakarta.validation.constraints.NotNull;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.Review;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    /**
     * 작성자: 오지수
     * 2024.09.08 : 주차장 상세페이지에 반환할 리뷰 목록
     * @param parkingLotId
     * @param pageable
     * @return
     */
    Page<Review> findByParkingLotId(Long parkingLotId, Pageable pageable);

    /**
     * 작성자: 오지수
     * @param user
     * @param pageable
     * @return
     */
    Page<Review> findByUser(User user, Pageable pageable);

//    Optional<Review> findByIdAndUser(Long reviewId, User user);
    /**
     * 작성자: 오지수
     * 2024.09.21: 주차장 관리자 페이지에서 리뷰 목록 가져오기
     * @param parkingLot
     * @param from
     * @param to
     * @param pageable
     * @return
     */
    Page<Review> findReviewsByParkingLotAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(ParkingLot parkingLot, LocalDateTime from, LocalDateTime to, Pageable pageable);

    // 1. 전체 리뷰 중에 complaintDate가 from과 to 사이에 있는 값들 (complaintDate가 null이면 안가져옴)
    Page<Review> findAllByComplaintDateBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

    // 2. 전체 리뷰 중에 complaint가 Bool.C 이고 complaintDate가 from과 to 사이에 있는 값들
    Page<Review> findByComplaintAndComplaintDateBetween(Bool complaint, LocalDateTime from, LocalDateTime to, Pageable pageable);

    // 3. 전체 리뷰 중에 complaint가 Bool.D이거나 Bool.Y이고 complaintDate가 from과 to 사이에 있는 값들
    Page<Review> findByComplaintInAndComplaintDateBetween(List<Bool> complaints, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
