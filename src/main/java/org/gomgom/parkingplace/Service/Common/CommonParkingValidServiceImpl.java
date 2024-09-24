package org.gomgom.parkingplace.Service.Common;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Exception.CustomExceptions;
import org.gomgom.parkingplace.Repository.ParkingLotRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonParkingValidServiceImpl implements CommonParkingValidService{
    private final ParkingLotRepository parkingLotRepository;

    /**
     * 작성자: 오지수
     * parkinglotId로 존재하는 주차장인지 확인
     * @param parkinglotId
     * @return
     */
    @Override
    public ParkingLot ifExistParkinglot(Long parkinglotId) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkinglotId)
                .orElseThrow(() ->  new CustomExceptions.ValidationException("존재하지 않는 주차장입니다."));

        return parkingLot;
    }

    /**
     * 작성자: 오지수
     * parkinglotId와 userId로 사용자의 주차장인지 확인하기
     * @param parkinglotId
     * @param userId
     * @return
     */
    @Override
    public ParkingLot ifValidParkinglotWithUser(Long parkinglotId, Long userId) {
        ParkingLot parkingLot = ifExistParkinglot(parkinglotId);

        if (!parkingLot.getUser().getId().equals(userId)) {
            throw new CustomExceptions.ValidationException("유효하지 않은 접근입니다.");
        }
        return parkingLot;
    }
}
