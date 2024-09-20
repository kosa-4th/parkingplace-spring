package org.gomgom.parkingplace.Service.parkingLot;

import jakarta.transaction.Transactional;
import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


@SpringBootTest
class ParkingLotServiceImplTest {
    @Autowired
    private ParkingLotService parkingLotService;

    @Test
    @Transactional
    public void testAddParkingLot() {
        Long parkingLotId = 1L;
        ParkingLotDto.ParkingLotDetailResponseDto dto =  parkingLotService.getParkingLotDetail(parkingLotId);
        System.out.println(dto.toString());
    }

    @Test
    @Rollback
    @DisplayName("나의 주차장 조회")
    public void myParkingLots() {
        ParkingLotDto.MyParkingLotsReponseDto dto = parkingLotService.getMyParkingLots(3L);
        for (ParkingLotDto.ParkingLotIdAndNameDto lot: dto.getParkingLots()) {
            System.out.println(lot.getId() + ", " + lot.getName());
        }
    }



}