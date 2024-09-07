package org.gomgom.parkingplace.Repository;


import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author 김경민
 * @date 2024-09-04
 *
 */

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

    List<ParkingLot> findByUsable (Bool usable);

    @Query("select p.name from ParkingLot p where p.id = :parkingLotId")
    String findByParkingLotName (Long parkingLotId);
}
