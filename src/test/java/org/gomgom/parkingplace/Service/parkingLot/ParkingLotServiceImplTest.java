package org.gomgom.parkingplace.Service.parkingLot;

import jakarta.transaction.Transactional;
import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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

}