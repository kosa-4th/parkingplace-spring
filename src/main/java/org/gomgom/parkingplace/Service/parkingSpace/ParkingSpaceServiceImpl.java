package org.gomgom.parkingplace.Service.parkingSpace;


import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Repository.ParkingSpaceRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

}
