package org.gomgom.parkingplace.Service.parkingSpace;


import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Repository.ParkingSpaceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author 김경민
 * @Date 2024.09.04
 *
 * */

@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    @Override
    
    public boolean isParkingSpaceAvailable(Long parkingLotId, LocalDateTime startTime, LocalDateTime endTime) {
        return false;
    }
}
