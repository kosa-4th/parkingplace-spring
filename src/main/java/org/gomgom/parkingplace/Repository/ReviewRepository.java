package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.Review;
import org.gomgom.parkingplace.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
