package org.gomgom.parkingplace.Service.parkingLot;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Repository.ParkingLotRepository;
import org.gomgom.parkingplace.enums.Bool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalTime;
import java.util.List;


@SpringBootTest
class ParkingLotServiceImplTest {
    @Autowired
    private ParkingLotService parkingLotService;
    @Autowired
    private ParkingLotRepository parkingLotRepository;


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

//    @Test
//    void getOwnerParkingLotDetail() {
//        ParkingLotDto.ParkingLotDetailResponseDto dto =  parkingLotService.getParkingLotDetail(1L);
//        System.out.println(dto.getParkingLotName());
//        List<String> list = dto.getImages();
//        System.out.println("사진 출력 시작");
//        System.out.println(list.size());
//        for (String image : list) {
//            System.out.println("이미지: " +image);
//        }
//    }


    @Test
    void modifyLotData() {
        ParkingLotDto.RequestModifyLotDto requestModifyLotDto = new ParkingLotDto.RequestModifyLotDto();
        requestModifyLotDto.setId(1781L);
        requestModifyLotDto.setName("성대주차장");
        requestModifyLotDto.setTel("02-1111-2222");
        requestModifyLotDto.setParkingType("민간주차장");
        requestModifyLotDto.setWeekdaysOpenTime(LocalTime.parse("18:00"));
        requestModifyLotDto.setWeekdaysCloseTime(LocalTime.parse("23:00"));
        requestModifyLotDto.setWeekendCloseTime(LocalTime.parse("22:00"));
        requestModifyLotDto.setWeekendOpenTime(LocalTime.parse("15:00"));
        requestModifyLotDto.setWash(Bool.Y);
        requestModifyLotDto.setMaintenance(Bool.N);

        ParkingLot parkingLot = parkingLotRepository.findById(requestModifyLotDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("해당 주차장을 찾을 수 없습니다."));

        parkingLot.setName(requestModifyLotDto.getName());
        parkingLot.setTel(requestModifyLotDto.getTel());
        parkingLot.setParkingType(requestModifyLotDto.getParkingType());
        parkingLot.setWeekdaysOpenTime(requestModifyLotDto.getWeekdaysOpenTime());
        parkingLot.setWeekdaysCloseTime(requestModifyLotDto.getWeekdaysCloseTime());
        parkingLot.setWeekendOpenTime(requestModifyLotDto.getWeekendOpenTime());
        parkingLot.setWeekendCloseTime(requestModifyLotDto.getWeekendCloseTime());
        parkingLot.setWash(requestModifyLotDto.getWash());
        parkingLot.setMaintenance(requestModifyLotDto.getMaintenance());

        parkingLotRepository.save(parkingLot);
    }
}