package org.gomgom.parkingplace.Service.Common;

import org.gomgom.parkingplace.Entity.ParkingLot;

public interface CommonParkingValidService {

    // 주차장 존재여부
    ParkingLot ifExistParkinglot(Long parkinglotId);

    // 주차장과 회원 정보 일치 여부 확인
    ParkingLot ifValidParkinglotWithUser(Long parkinglotId, Long userId);
}
