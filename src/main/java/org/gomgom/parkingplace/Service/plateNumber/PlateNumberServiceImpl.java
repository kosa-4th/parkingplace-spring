package org.gomgom.parkingplace.Service.plateNumber;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gomgom.parkingplace.Dto.PlateNumberDto;
import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.Entity.PlateNumber;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Exception.CustomExceptions;
import org.gomgom.parkingplace.Repository.CarTypeRepository;
import org.gomgom.parkingplace.Repository.PlateNumberRepository;
import org.gomgom.parkingplace.Repository.UserRepository;
import org.gomgom.parkingplace.enums.CarTypeEnum;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class PlateNumberServiceImpl implements PlateNumberService{
    private final PlateNumberRepository plateNumberRepository;
    private final CarTypeRepository carTypeRepository;

    /*
    작성자: 오지수
    내 차량 정보 불러오기
     */
    @Override
    public Map<String, Object> getPlateNumber(User user) {
        log.info("getPlateNumber service!!!");
        Map<String, Object> map = new HashMap<>();
        record CarTypeResponse(String carType) {}
        map.put("carTypes", CarTypeEnum.getFilteredCarTypes().stream()
                        .map(CarTypeResponse::new)
                                .toList());
        log.info("put carTypes");
        map.put("myCars", plateNumberRepository.findByUser(user).stream()
                .map(PlateNumberDto.ResponseMyCarDto::new)
                .toList());

        log.info("put myCars");

        return map;
    }

    /*
    작성자: 오지수
    내 차량 번호 등록하기
     */
    @Override
    public void registerPlateNumber(User user, PlateNumberDto.RequestMyCarDto carDto) {
        if (plateNumberRepository.existsByPlateNumberAndUser(carDto.getPlateNumber(), user)) {
            throw new CustomExceptions.ValidationException("이미 등록된 차량입니다.");
        }

        CarType carType = carTypeRepository.findByCarTypeEnum(CarTypeEnum.fromKorName(carDto.getCarType()));
        if (carType == null) {
            throw new CustomExceptions.ValidationException("잘못된 차량 정보입니다.");
        }
        PlateNumber plateNumber = PlateNumber.builder()
                .user(user)
                .plateNumber(carDto.getPlateNumber())
                .carType(carType)
                .build();
        plateNumberRepository.save(plateNumber);
    }

    /*
    내 차량 삭제하기
     */
    @Override
    public void deletePlateNumber(User user, PlateNumberDto.RequestMyCarDto carDto) {
        List<PlateNumber> plateNumbers = plateNumberRepository.findByUser(user);
        if (plateNumbers.size() <= 1) {
            throw new IllegalStateException("차량은 최소 한 대 이상 등록되어 있어야 합니다.");
        }

        PlateNumber targetCar = plateNumbers.stream()
                .filter(car -> car.getPlateNumber().equals(carDto.getPlateNumber()))
                .findFirst()
                .orElseThrow(() -> new CustomExceptions.ValidationException("해당 차량 번호가 등록되어 있지 않습니다."));

        System.out.println("targetCar = " + targetCar.getCarType().getCarTypeEnum());
        if (!targetCar.getCarType().getCarTypeEnum().equals(CarTypeEnum.fromKorName(carDto.getCarType()))) {
            throw new IllegalArgumentException("차량 종류가 일치하지 않습니다.");
        }

        plateNumberRepository.delete(targetCar);
    }
}
