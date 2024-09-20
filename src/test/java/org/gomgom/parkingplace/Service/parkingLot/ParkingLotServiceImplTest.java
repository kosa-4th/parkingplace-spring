package org.gomgom.parkingplace.Service.parkingLot;

import jakarta.transaction.Transactional;
import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;


@SpringBootTest
class ParkingLotServiceImplTest {
    @Autowired
    private ParkingLotService parkingLotService;

//    @Test
//    @Transactional
//    public void testAddParkingLot() {
//        Long parkingLotId = 1L;
//        ParkingLotDto.ParkingLotDetailResponseDto dto =  parkingLotService.getParkingLotDetail(parkingLotId);
//        System.out.println(dto.toString());
//    }
//
//    @Test
//    @Rollback
//    @DisplayName("나의 주차장 조회")
//    public void myParkingLots() {
//        ParkingLotDto.MyParkingLotsReponseDto dto = parkingLotService.getMyParkingLots(3L);
//        for (ParkingLotDto.ParkingLotIdAndNameDto lot: dto.getParkingLots()) {
//            System.out.println(lot.getId() + ", " + lot.getName());
//        }
//    }

    @Test
    void getOwnerParkingLotDetail() {
        ParkingLotDto.ParkingLotDetailResponseDto dto =  parkingLotService.getParkingLotDetail(1L);
        System.out.println(dto.getParkingLotName());
        List<String> list = dto.getImages();
        System.out.println("사진 출력 시작");
        System.out.println(list.size());
        for (String image : list) {
            System.out.println("이미지: " +image);
        }
    }



}