package org.gomgom.parkingplace.Service.reservation;

import lombok.RequiredArgsConstructor;
import static org.gomgom.parkingplace.Dto.ReservationDto.RequestAvailableDto;

import org.apache.tomcat.util.net.openssl.OpenSSLUtil;
import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.Entity.ParkingSpace;
import org.gomgom.parkingplace.Repository.CarTypeRepository;
import org.gomgom.parkingplace.Repository.ParkingSpaceRepository;
import org.gomgom.parkingplace.Repository.ReservationRepository;
import org.gomgom.parkingplace.enums.CarTypeEnum;
import org.springframework.stereotype.Service;
import static org.gomgom.parkingplace.Dto.ReservationDto.ReservationAvailableResponseDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingSeatSearchServiceImpl implements ParkingSeatSearchService {

    private final ReservationRepository reservationRepository;
    private final CarTypeRepository carTypeRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;

    /**
     * 주차장의 차종에 따른 자리 개수와 예약 가능한 주차 공간을 조회하는 메서드.
     * @Author 김겸민
     * @Date 2024.09.05
     * @return              예약 가능한 주차 공간(ParkingSpace)의 리스트.
     */
    public List<ParkingSpace> findAvailableParkingSpaces(RequestAvailableDto requestAvailableDto) {

        //먼저 주차장 내에 예약 된 주차장 자리 조회
        List<Object[]> result =  reservationRepository.findAvailableSpaces(requestAvailableDto.getParkingLotId(), requestAvailableDto.getStartTime(), requestAvailableDto.getEndTime());

        for(int i = 0; i < result.size(); i++) {
            Object[] row = result.get(i);  // i번째 Object[] 배열 가져오기

            // 각 배열의 요소에 접근 (예: carTypeId, availableSpaceNum 등)
            Long carTypeId = (Long) row[0];         // 첫 번째 값
            Long availableSpaceNum = (Long) row[1]; // 두 번째 값

            // 출력
            System.out.println("Index: " + i + " | CarTypeId: " + carTypeId + " | AvailableSpaceNum: " + availableSpaceNum);
        }
        // 빈 ParkingSpace 리스트 생성
        List<ParkingSpace> availableSpaces = new ArrayList<>();
        // Object[] 배열을 ParkingSpace로 변환하여 리스트에 추가
        for (Object[] row : result) {
            // row[0] : carTypeId, row[1] : availableSpaceNum
            Long carTypeId = (Long) row[0];
            Long availableSpaceNum = (Long) row[1];
            Long parkingLotId = requestAvailableDto.getParkingLotId();
            // ParkingSpace 객체 생성
            ParkingSpace parkingSpace = parkingSpaceRepository.findByParkingLotAndCarType(parkingLotId, carTypeId)
                    .orElse(null);  // 먼저 주차장과 차량 타입에 맞는 ParkingSpace를 조회
            System.out.println("ParkingSpace객체 생성...한다."+parkingSpace.toString());
            // 차량 타입(CarType)을 carTypeId를 통해 조회
            CarType carType = carTypeRepository.findById(carTypeId).orElse(null);

            // 먼저 주어진 차량 타입에 맞는 공간을 찾음
            if (carType != null && carType.getId().equals(carTypeId)) {
                parkingSpace.setCarType(carType);  // 주어진 차량 타입에 맞는 공간 설정
            } else {
                // 차량 타입이 없거나 맞는 공간이 없으면, 'ALL' 차량 공간으로 대체
                carType = carTypeRepository.findByCarTypeEnum(CarTypeEnum.ALL);
                parkingSpace.setCarType(carType);  // ALL 차량 공간 설정
            }

            // 남은 주차 공간 수 설정
            parkingSpace.setAvailableSpaceNum(availableSpaceNum.intValue());

            // 결과 리스트에 추가
            availableSpaces.add(parkingSpace);
        }

        // ParkingSpace 리스트 반환
        return availableSpaces;
    }


    /**
     * 특정 주차장의 특정 차종에 대해 예약 가능한 주차 공간이 있는지 확인하는 메서드.
     * 만약 특정 차종에 맞는 자리가 없으면, 모든 차종(CarTypeEnum.ALL) 주차 공간을 확인.
     * @Author 김겸민
     * @Date 2024.09.05

     * @return              예약 가능한 주차 공간이 있으면 true, 없으면 false.
     */
    public ReservationAvailableResponseDto isParkingSpaceAvailable(RequestAvailableDto requestAvailableDto) {

        List <ParkingSpace> availableSpaces = findAvailableParkingSpaces(requestAvailableDto);
        int totalFee = 0;
        ParkingCalculator parkingCalculator = new ParkingCalculator();
        //차종에 맞는 자리 확인
        for (ParkingSpace space : availableSpaces) {
            CarType carType = requestAvailableDto.getCarType();

            if (space.getCarType().equals(carType.getId()) && space.getAvailableSpaceNum() > 0) {
                System.out.println("차종에 맞는 자리 : " + space.getAvailableSpaceNum());
                totalFee = parkingCalculator.calculatorParkingFee(space, requestAvailableDto);
                return new ReservationAvailableResponseDto(true, totalFee);

            }else if(space.getCarType().getId().equals(carType.getId()) && space.getAvailableSpaceNum()>0){
                System.out.println("맞는 자리 :"+ space.getAvailableSpaceNum());
                totalFee = parkingCalculator.calculatorParkingFee(space, requestAvailableDto);

                return new ReservationAvailableResponseDto(true, totalFee);
            }
        }
        System.out.println("남은 자리 없음.");
        return new ReservationAvailableResponseDto(false, totalFee);
    }
}
