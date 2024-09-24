package org.gomgom.parkingplace.Service.parkingSpace;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.gomgom.parkingplace.Dto.ParkingSpaceDto;
import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.ParkingSpace;
import org.gomgom.parkingplace.Repository.CarTypeRepository;
import org.gomgom.parkingplace.Repository.ParkingLotRepository;
import org.gomgom.parkingplace.Repository.ParkingSpaceRepository;
import org.gomgom.parkingplace.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final CarTypeRepository carTypeRepository;

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.20
     * 설명 : 주차 구역 추가
     *  ---------------------
     * 2024.09.20 양건모 | 기능 구현
     * */
    @Override
    public void insertParkingSpace(long userId, ParkingSpaceDto.InsertParkingSpaceRequestDto request) throws BadRequestException {
        ParkingLot parkingLot = parkingLotRepository.findById(request.getParkingLotId()).orElseThrow();
        CarType carType = carTypeRepository.findById(request.getCarTypeId()).orElseThrow();

        if (!parkingLot.getUser().getId().equals(userId)) {
            throw new BadRequestException();
        }

        ParkingSpace parkingSpace = new ParkingSpace(
                parkingLot,
                request.getSpaceName(),
                carType,
                request.getAvailableSpaceNum(),
                request.getWeekdaysPrice(),
                request.getWeekAllDayPrice(),
                request.getWeekendPrice(),
                request.getWeekendAllDayPrice(),
                request.getWashPrice(),
                request.getMaintenancePrice()
        );
        parkingSpaceRepository.save(parkingSpace);
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.21
     * 설명 : 주차 구역 수정
     *  ---------------------
     * 2024.09.21 양건모 | 기능 구현
     * */
    @Override
    @Transactional
    public void modifyParkingSpace(long userId, ParkingSpaceDto.ModifyParkingSpaceRequestDto request) throws BadRequestException {
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(request.getParkingSpaceId()).orElseThrow();
        CarType carType = carTypeRepository.findById(request.getCarTypeId()).orElseThrow();

        if (!parkingSpace.getParkingLot().getUser().getId().equals(userId)) {
            throw new BadRequestException();
        }
        parkingSpace.setAllValues(request, carType);
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.21
     * 설명 : 주차 구역 삭제
     *  ---------------------
     * 2024.09.21 양건모 | 기능 구현
     * */
    @Override
    @Transactional
    public void deleteParkingSpace(long userId, long parkingSpaceId) throws BadRequestException {
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(parkingSpaceId).orElseThrow();

        if (!parkingSpace.getParkingLot().getUser().getId().equals(userId)) {
            throw new BadRequestException();
        }
        parkingSpace.setUsable(false);
        System.out.println(parkingSpace.isUsable() + "===================");
    }
}
