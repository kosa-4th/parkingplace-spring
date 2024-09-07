package org.gomgom.parkingplace.Service.reservation;


import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Repository.ParkingLotRepository;
import org.gomgom.parkingplace.Repository.PlateNumberRepository;
import org.gomgom.parkingplace.Repository.ReservationRepository;
import org.gomgom.parkingplace.Repository.UserRepository;
import org.gomgom.parkingplace.util.CustomUUIDGenerator;
import org.springframework.stereotype.Service;


/**
 * ReservationServiceImpl.java
 *
 * @author 김경민
 * @date 2024-09-03
 */
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CustomUUIDGenerator customUUIDGenerator;
    private final PlateNumberRepository plateNumberRepository;
    private final ParkingLotRepository parkingLotRepository;

    @Override
    public boolean existsByReservationUuid(String uuid) {
        return reservationRepository.existsByReservationUuid(uuid);
    }


}
