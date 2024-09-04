package org.gomgom.parkingplace.Controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.ReservationDto;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.Service.parkingLot.ParkingLotService;
import org.gomgom.parkingplace.Service.reservation.ReservationService;
import org.gomgom.parkingplace.Service.reservation.ReservationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parkingLots/{parkingLotId}")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
//    private final ParkingLotService parkingLotService;
//
//
//    @GetMapping("/reservation")
//    public ResponseEntity<ReservationDto.ResponseReservationDto> getReservation(@RequestParam Long id) {
//        Reservation reservation = reservationService.(id);
//        return ResponseEntity.ok(reservation);
//    }
}
