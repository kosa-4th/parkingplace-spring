package org.gomgom.parkingplace.Repository;


import org.gomgom.parkingplace.Entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
}
