package org.gomgom.parkingplace.Service.parkingSpace;


import org.apache.coyote.BadRequestException;
import org.gomgom.parkingplace.Dto.ParkingSpaceDto;

public interface ParkingSpaceService {

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.20
     * 설명 : 주차 구역 추가
     *  ---------------------
     * 2024.09.20 양건모 | 기능 구현
     * */
    void insertParkingSpace(long userId, ParkingSpaceDto.InsertParkingSpaceRequestDto request) throws BadRequestException;

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.21
     * 설명 : 주차 구역 수정
     *  ---------------------
     * 2024.09.21 양건모 | 기능 구현
     * */
    void modifyParkingSpace(long userId, ParkingSpaceDto.ModifyParkingSpaceRequestDto request) throws BadRequestException;

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.21
     * 설명 : 주차 구역 수정
     *  ---------------------
     * 2024.09.21 양건모 | 기능 구현
     * */
    void deleteParkingSpace(long userId, long parkingSpaceId) throws BadRequestException;
}
