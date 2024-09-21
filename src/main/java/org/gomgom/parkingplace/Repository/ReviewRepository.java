package org.gomgom.parkingplace.Repository;

import jakarta.validation.constraints.NotNull;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.Review;
import org.gomgom.parkingplace.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

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
}
