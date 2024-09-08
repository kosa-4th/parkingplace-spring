package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.Review;
import org.gomgom.parkingplace.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
//    List<Review> findByParkingLotId(long parkingLotId);
    Page<Review> findByParkingLotId(Long parkingLotId, Pageable pageable);

    Optional<Review> findByIdAndUser(Long reviewId, User user);
}
