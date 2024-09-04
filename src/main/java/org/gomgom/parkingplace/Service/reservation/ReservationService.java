package org.gomgom.parkingplace.Service.reservation;

import org.gomgom.parkingplace.Dto.ReservationDto;
import org.gomgom.parkingplace.Entity.Reservation;

/**
 * ReservationService.java
 *
 * @author 김경민
 * @date 2024-09-03
 */
public interface ReservationService {
//    public List<Reservation> getReservationsByEmail(String email);



    boolean existsByReservationUuid(String uuid);

    void insertReservationData(ReservationDto.RequestReservationDto requestReservationDto);
}