package org.gomgom.parkingplace.Service.reservation;


import org.gomgom.parkingplace.Dto.ReservationDto;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.PlateNumber;
import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Repository.ParkingLotRepository;
import org.gomgom.parkingplace.Repository.PlateNumberRepository;
import org.gomgom.parkingplace.Repository.ReservationRepository;
import org.gomgom.parkingplace.Repository.UserRepository;
import org.gomgom.parkingplace.util.CustomUUIDGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ReservationServiceImpl.java
 *
 * @author 김경민
 * @date 2024-09-03
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CustomUUIDGenerator customUUIDGenerator;

    private final PlateNumberRepository plateNumberRepository;
    private final ParkingLotRepository parkingLotRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, CustomUUIDGenerator customUUIDGenerator, PlateNumberRepository plateNumberRepository, ParkingLotRepository parkingLotRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.customUUIDGenerator = customUUIDGenerator;
        this.plateNumberRepository = plateNumberRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    @Override
    public boolean existsByReservationUuid(String uuid) {
        return reservationRepository.existsByReservationUuid(uuid);
    }

    @Override
    public void insertReservationData(ReservationDto.RequestReservationDto requestReservationDto) {
        String customUUID = customUUIDGenerator.generateUniqueUUID();
        // User, ParkingLot, PlateNumber를 데이터베이스에서 조회
        User user = userRepository.findById(requestReservationDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        ParkingLot parkingLot = parkingLotRepository.findById(requestReservationDto.getParkingLotId())
                .orElseThrow(() -> new RuntimeException("Parking lot not found"));
        PlateNumber plateNumber = plateNumberRepository.findById(requestReservationDto.getPlateNumberId())
                .orElseThrow(() -> new RuntimeException("Plate number not found"));

        Reservation reservation = Reservation.builder()
                .user(user)
                .parkingLot(parkingLot)
                .plateNum(plateNumber)
                .startTime(requestReservationDto.getStartTime())
                .endTime(requestReservationDto.getEndTime())
                .reservationUuid(customUUID)
                .wash(requestReservationDto.getWash())
                .maintenance(requestReservationDto.getMaintenance())
                .totalPrice(requestReservationDto.getTotalPrice())
                .build();
        reservationRepository.save(reservation);
    }
}
