package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author : 김경민
 * @date : 2024-09-04
 *
 * */
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {

}
