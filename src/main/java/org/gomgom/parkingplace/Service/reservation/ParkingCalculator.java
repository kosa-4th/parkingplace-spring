package org.gomgom.parkingplace.Service.reservation;

import org.gomgom.parkingplace.Dto.ReservationDto;
import org.gomgom.parkingplace.Entity.ParkingSpace;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.gomgom.parkingplace.enums.Bool.Y;

public class ParkingCalculator {

    /**
     * 주차 시작 시간, 종료 시간, 그리고 세차 및 정비 서비스 여부를 기반으로
     * 총 주차 요금을 계산하는 메서드.
     *

     * @return 계산된 총 주차 요금 (종일권, 시간별 요금, 부가 서비스 포함).
     * @Author 김경민
     * @Date 2024.09.05
     */
    public int calculatorParkingFee(ParkingSpace parkingSpace, ReservationDto.RequestAvailableDto requestAvailableDto) {

        int totalAmount = 0;
        //먼저 30분 단위로 요금 계산
        long parkingMinutes = ChronoUnit.MINUTES.between(requestAvailableDto.getStartTime(), requestAvailableDto.getEndTime());

        long halfHourUnits = (long) Math.ceil((double) parkingMinutes / 30);

        System.out.println(requestAvailableDto.getStartTime());
        System.out.println(requestAvailableDto.getEndTime());
        System.out.println(parkingSpace.toString());
        System.out.println(halfHourUnits + "halfHourUnits");

        for (int i = 0; i < halfHourUnits; i++) {
            int rate = getRateForTime(parkingSpace, requestAvailableDto.getStartTime());
            totalAmount += rate;
            System.out.println("totalAmount" + ": i :" + totalAmount);
        }

        int oneDayCoupon = getOneDayCouponPrice(parkingSpace, requestAvailableDto.getStartTime());
        if (oneDayCoupon != 0 && totalAmount > oneDayCoupon) {

            System.out.println("종일권 가격보다 높은 가격입니다.");
            totalAmount = oneDayCoupon;

        }

        if (requestAvailableDto.getWashService() == Y) {
            System.out.println("세차여부 확인");
            totalAmount += (parkingSpace.getWashPrice() != null) ? parkingSpace.getWashPrice() : 0;

        }
        if (requestAvailableDto.getMaintenanceService() == Y) {
            System.out.println("기본정비 확인");
            totalAmount += (parkingSpace.getMaintenancePrice() != null) ? parkingSpace.getMaintenancePrice() : 0;
        }

        System.out.println("totalFee" + totalAmount);
        return totalAmount;
    }

    /**
     * @param startTime 주차 시작 시간.
     * @return 주중이면 평일 요금, 주말이면 주말 요금.
     * @Author 김경민
     * @Date 2024.09.05
     * 시작 날짜를 통해 시작 시간에 해당하는 주차 요금을 반환하는 메서드.
     * 요일에 따라 주중 요금 또는 주말 요금을 반환합니다.
     */
    private int getRateForTime(ParkingSpace parkingSpace, LocalDateTime startTime) {
        //날짜에 따른 주중 / 주말
        DayOfWeek dayOfWeek = startTime.getDayOfWeek();

        // 주중/주말 요금을 반환
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return parkingSpace.getWeekendPrice() != null ? parkingSpace.getWeekendPrice() : 0;  // 주말 요금
        } else {
            return parkingSpace.getWeekdaysPrice() != null ? parkingSpace.getWeekdaysPrice() : 0;  // 평일 요금
        }
    }

    /**
     * @param startTime 주차 시작 시간.
     * @return 주중이면 평일 종일권 요금, 주말이면 주말 종일권 요금.
     * @Author 김경민
     * @Date 2024.09.05
     * 주차 시간이 종일권 요금보다 높을 때, 주어진 날짜에 해당하는 종일권 요금을 반환하는 메서드.
     * 주중이면 평일 종일권 요금, 주말이면 주말 종일권 요금을 반환합니다.
     */
    private int getOneDayCouponPrice(ParkingSpace parkingSpace, LocalDateTime startTime) {
        //날짜에 따른 주중 / 주말
        DayOfWeek dayOfWeek = startTime.getDayOfWeek();

        // 주중/주말 종일권 요금을 반환
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return parkingSpace.getWeekendAllDayPrice() != null ? parkingSpace.getWeekendAllDayPrice() : 0;  // 주말 종일권 요금
        } else {
            return parkingSpace.getWeekAllDayPrice() != null ? parkingSpace.getWeekAllDayPrice() : 0;  // 평일 종일권 요금
        }
    }

}
