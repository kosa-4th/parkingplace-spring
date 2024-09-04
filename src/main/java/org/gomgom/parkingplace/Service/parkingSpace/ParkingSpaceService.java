package org.gomgom.parkingplace.Service.parkingSpace;

import java.time.LocalDateTime;

public interface ParkingSpaceService {
    boolean isParkingSpaceAvailable(Long parkingLotId, LocalDateTime startTime, LocalDateTime endTime);

}
