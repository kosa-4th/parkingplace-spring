package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.PlateNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlateNumberRepository extends JpaRepository<PlateNumber, Long> {
}
