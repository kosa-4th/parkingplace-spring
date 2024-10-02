package org.gomgom.parkingplace.Service.reservation;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Entity.Reservation;
import org.gomgom.parkingplace.Repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KpiReservationServiceImpl implements KpiReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public Map<String, Map<String, Long>> get7DaysIncome(Long parkingLotId) {

        LocalDate endOfThisWeek = LocalDate.now(); // 오늘 날짜
        LocalDate startOfThisWeek = endOfThisWeek.minusDays(6); // 오늘로부터 -7일
        LocalDate endOfLastWeek = startOfThisWeek;
        LocalDate startOfLastWeek = endOfLastWeek.minusDays(6); // 지난주 데이터 비교

        Map<String, Long> thisWeekReservations = getWeeklyIncome(parkingLotId, startOfThisWeek, endOfThisWeek);
        Map<String, Long> lastWeekReservations = getWeeklyIncome(parkingLotId, startOfLastWeek, endOfLastWeek);

        // 이번 주와 지난 주 데이터를 Map으로 묶어서 반환
        return Map.of("thisWeek", thisWeekReservations, "lastWeek", lastWeekReservations);
    }

    /**
     * @Date 2024.09.30
     * 예정, 입출차예정, 차량 정보 count가져오기
     * */
    @Override
    public Map<String, Long> getReservationStatusCounts(Long parkingLotId) {
        LocalDateTime currentTime = LocalDateTime.now();

        Long pendingReservations = reservationRepository.countPendingReservations(parkingLotId, currentTime);
        Long checkInReservations = reservationRepository.countCheckInReservations(parkingLotId, currentTime);
        Long checkOutReservations = reservationRepository.countCheckOutReservations(parkingLotId, currentTime);

        Map<String, Long> countResult = new HashMap<>();
        countResult.put("pendingReservations", pendingReservations);
        countResult.put("checkInReservations", checkInReservations);
        countResult.put("checkOutReservations", checkOutReservations);
        return countResult;
    }

    /**
     * @Date 2024.09.28
     * 7일간의 에약 건수
     */
    @Override
    public Map<String, Map<String, Long>> getLast7DaysReservations(Long parkingLotId) {
        LocalDate endOfThisWeek = LocalDate.now(); // 오늘 날짜
        LocalDate startOfThisWeek = endOfThisWeek.minusDays(6); // 오늘로부터 -7일
        LocalDate endOfLastWeek = startOfThisWeek;
        LocalDate startOfLastWeek = endOfLastWeek.minusDays(6); // 지난주 데이터 비교

        Map<String, Long> thisWeekReservations = getWeeklyReservations(parkingLotId, startOfThisWeek, endOfThisWeek);
        Map<String, Long> lastWeekReservations = getWeeklyReservations(parkingLotId, startOfLastWeek, endOfLastWeek);

        // 이번 주와 지난 주 데이터를 Map으로 묶어서 반환
        return Map.of("thisWeek", thisWeekReservations, "lastWeek", lastWeekReservations);
    }

    /**
     * @Date 2024.09.28 데이터를 일별로 그룹화
     */
    private Map<String, Long> getWeeklyReservations(Long parkingLotId, LocalDate startDate, LocalDate endDate) {
        // 데이터베이스에서 해당 기간 동안의 예약 데이터를 가져옴
        List<Reservation> reservations = reservationRepository.findReservationsByDateRange(parkingLotId, startDate, endDate);

        // 날짜별 예약 카운트 맵 (예약이 없는 날짜는 0으로 처리)
        Map<String, Long> completeReservations = new HashMap<>();

        // startDate부터 endDate까지 날짜별로 0으로 초기화
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            completeReservations.put(date.toString(), 0L); // 모든 날짜를 0으로 초기화
        }

        // 실제 예약 데이터를 처리하여 Map을 업데이트
        for (Reservation reservation : reservations) {
            LocalDate reservationDate = reservation.getStartTime().toLocalDate();
            completeReservations.merge(reservationDate.toString(), 1L, Long::sum); // 예약이 있는 날짜는 카운트 추가
        }

        return completeReservations; // 모든 날짜와 예약 건수(0 포함)가 포함된 맵 반환
    }

    /*
    * 일별 수익 계산
    * */
    private Map<String, Long> getWeeklyIncome(Long parkingLotId, LocalDate startDate, LocalDate endDate) {
        // 데이터베이스에서 해당 기간 동안의 예약 데이터를 가져옴
        List<Reservation> reservations = reservationRepository.findReservationsByDateRange(parkingLotId, startDate, endDate);

        // 날짜별 수익 맵 (수익이 없는 날짜는 0으로 처리)
        Map<String, Long> dailyRevenue = new HashMap<>();

        // startDate부터 endDate까지 날짜별로 0으로 초기화
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            dailyRevenue.put(date.toString(), 0L); // 모든 날짜를 0으로 초기화
        }

        // 실제 예약 데이터를 처리하여 Map을 업데이트 (수익을 더함)
        for (Reservation reservation : reservations) {
            LocalDate reservationDate = reservation.getStartTime().toLocalDate();
            dailyRevenue.merge(reservationDate.toString(), reservation.getTotalPrice().longValue(), Long::sum); // 예약이 있는 날짜는 수익을 합산
        }

        return dailyRevenue; // 모든 날짜와 수익(0 포함)이 포함된 맵 반환
    }
}
