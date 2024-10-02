package org.gomgom.parkingplace.Service.reservation;

import java.util.Map;


public interface KpiReservationService {

    Map<String, Map<String, Long>> getLast7DaysReservations(Long parkingLotId);  // 7일간의 예약 건수

     Map<String, Long> getReservationStatusCounts(Long parkingLotId);

     Map<String, Map<String, Long>> get7DaysIncome(Long parkingLotId);
}
