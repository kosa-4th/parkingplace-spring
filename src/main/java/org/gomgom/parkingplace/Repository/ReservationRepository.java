package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    //userID값을 통한 예약 내회 조회
    List<Reservation> findByUserId(Long userId);
    boolean existsByReservationUuid(String uuid);



}
