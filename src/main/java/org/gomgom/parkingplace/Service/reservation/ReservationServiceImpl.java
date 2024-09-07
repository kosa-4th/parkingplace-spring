package org.gomgom.parkingplace.Service.reservation;


import lombok.RequiredArgsConstructor;
import static org.gomgom.parkingplace.Dto.ReservationDto.RequestReservationDto;

import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.ParkingSpace;
import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Repository.*;
import org.gomgom.parkingplace.enums.Bool;
import org.gomgom.parkingplace.util.CustomUUIDGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


/**
 * ReservationServiceImpl.java
 *
 * 예약 저장하는 로직
 *
 * @author 김경민
 * @date 2024-09-07
 */
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CustomUUIDGenerator customUUIDGenerator;
    private final PlateNumberRepository plateNumberRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;


    /**
     * @Author 김경민
     * @Date 2024.09.07
     *
     * 예약 저장하는 로직
     *
     * */
    @Transactional
    public Reservation createReservation(RequestReservationDto requestReservationDto){
        Reservation reservation = new Reservation();

        System.out.println("parkingLotName 여기인가?");

        String parkingLotName = parkingLotRepository.findByParkingLotName(requestReservationDto.getParkingLotId());

        System.out.println("UUID 여기인가?");

        String reservationUuid = customUUIDGenerator.generateUniqueUUID();

        System.out.println("parkingSPace 여기인가?");

        ParkingSpace parkingSpace = parkingSpaceRepository.findById(requestReservationDto.getParkingSpaceId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Parking Space ID"));

        System.out.println("USER 여기인가?");

        User user = userRepository.findByEmail(requestReservationDto.getUserEmail())
                .orElseThrow(() ->new IllegalArgumentException("Email 존재 안함"));

        System.out.println("parkingLot 여기인가?");

        ParkingLot parkingLot = parkingLotRepository.findById(requestReservationDto.getParkingLotId())
                        .orElseThrow(()->new IllegalArgumentException("parkingLot 존재 안함."));


        System.out.println("===============변수");
        String plateNumber = requestReservationDto.getPlateNumber();
        LocalDateTime startTime =requestReservationDto.getStartTime();
        LocalDateTime endTime =requestReservationDto.getEndTime();
        Bool wash = requestReservationDto.getWashService();
        Bool maintenance =  requestReservationDto.getMaintenanceService();
        int totalPrice = requestReservationDto.getTotalPrice();

        System.out.println("=============Set");
        reservation.setLotName(parkingLotName);
        reservation.setReservationUuid(reservationUuid);
        reservation.setPlateNumber(plateNumber);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setWash(wash);
        reservation.setMaintenance(maintenance);
        reservation.setTotalPrice(totalPrice);
        reservation.setUser(user);
        reservation.setParkingLot(parkingLot);
        reservation.setParkingSpace(parkingSpace);
        reservation.setReservationConfirmed(Bool.N);
        System.out.println("reservation : " +reservation.toString());
        return reservationRepository.save(reservation);

    }



}
