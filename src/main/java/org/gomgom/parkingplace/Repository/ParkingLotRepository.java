package org.gomgom.parkingplace.Repository;


import org.gomgom.parkingplace.Entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author 김경민
 * @date 2024-09-04
 *
 */
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

}
