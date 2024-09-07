package org.gomgom.parkingplace.Service.parkingSpace;

import org.gomgom.parkingplace.Dto.ParkingLotDto;

import java.time.LocalDateTime;

public interface ParkingSpaceService {
    boolean isParkingSpaceAvailable(Long parkingLotId, LocalDateTime startTime, LocalDateTime endTime);
}
