package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByParkingLotId(long parkingLotId);
}
