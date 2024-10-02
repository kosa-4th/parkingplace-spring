package org.gomgom.parkingplace.Controller.ParkingManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gomgom.parkingplace.Service.reservation.KpiReservationService;
import org.gomgom.parkingplace.Service.reservation.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/parking-manager/parkingLots/{parkingLotId}")
@RequiredArgsConstructor
@Log4j2
public class ParkingGraphController {

    private final ReservationService reservationService;
    private final KpiReservationService kpiReservationService;

    @GetMapping("/kpi")
//    @PreAuthorize("hasRole('ROLE_PARKING_MANAGER')")
    public ResponseEntity<Map<String, Object>> getKpiData(@PathVariable Long parkingLotId) {
        // 지난 7일간의 예약 건수 가져오기
        Map<String, Map<String, Long>> reservationCounts = kpiReservationService.getLast7DaysReservations(parkingLotId);

        // 현재 예약 상태(대기, 입차, 출차) 건수 가져오기
        Map<String, Long> reservationStatusCounts = kpiReservationService.getReservationStatusCounts(parkingLotId);

        Map<String, Map<String, Long>> reservationIncome = kpiReservationService.get7DaysIncome(parkingLotId);

        // 두 결과를 합쳐서 반환
        Map<String, Object> result = new HashMap<>();
        result.put("reservationCounts", reservationCounts);
        result.put("reservationStatusCounts", reservationStatusCounts);
        result.put("reservationIncome", reservationIncome);
        // 데이터를 성공적으로 반환
        return ResponseEntity.ok(result);
    }
}
