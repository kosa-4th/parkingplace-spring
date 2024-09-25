package org.gomgom.parkingplace.Service.parkingLot;

import org.apache.coyote.BadRequestException;
import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ParkingLotService {
    /***
     * @Author 김경민
     * @Date 2024.09.25
     *
     * 주차장 데이터 수정
     */
    int createLotData(ParkingLotDto.RequestCreateLotDto requestCreateLotDto);

    /***
     * @Author 김경민
     * @Date 2024.09.24
     *
     * 주차장 데이터 수정
     */
    int modifyLotData(ParkingLotDto.RequestModifyLotDto requestModifyLotDto);

    /**
     * @Author 김경민
     * @Date 2024.09.23
     *
     * ParkingLot Data Paging 및 검색어 처리
     * */
    Page<ParkingLotDto.ResponseParkingLotDto> getParkingLotData(String name, String address, Pageable pageable);


    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.02
     * 설명 : 위도 경도 범위 내의 주차장들의 위도, 경도를 포함한 정보 제공
     * @param request 최소 위도, 최대 위도, 최소 경도, 최대 경도
     * @return List 형태로 id, 이름, 위도, 경도, 주소
     *  ---------------------
     * 2024.09.05 양건모 | 기능 구현
     * 2024.09.07 양건모 | 반환 DTO 이름 변경에 대한 적용
     * */
    ParkingLotDto.ParkingLotMarkersResponseDto getParkingLots(ParkingLotDto.ParkingLotListRequestDto request);

    /*
    작성자: 오지수
    주차장 상세 정보 가져오기
     */
    ParkingLotDto.ParkingLotDetailResponseDto getParkingLotDetail(Long parkingLotId);

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.07
     * 설명 : 주차장의 간략한 정보 제공
     * @param parkingLotId 주차장 id
     * @return 주차장명, 주소, 가격, 리뷰 개수, 가장 최신 리뷰, 주차 구역 목록
     *  ---------------------
     * 2024.09.07 양건모 | 기능 구현
     * */
    ParkingLotDto.ParkingLotPreviewResponseDto getParkingLotPreview(Long parkingLotId);

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.15
     * 설명 : 사용자 id로 등록된 주차장 목록 조회
     * @param userId 사용자 id
     * @return List 형태로 주차장 id, 주차장 이름
     *  ---------------------
     * 2024.09.15 양건모 | 기능 구현
     * */
    ParkingLotDto.MyParkingLotsReponseDto getMyParkingLots(Long userId);

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.18
     * 설명 : 주차구역, 이미지를 포함한 사용자가 소유한 주차장 상세 정보 조회
     * @param userId 사용자 id
     * @param parkingLotId 주치장 id
     * @return 주차장 상세 정보
     *  ---------------------
     * 2024.09.18 양건모 | 기능 구현
     * */
    ParkingLotDto.OwnerParkingLotDetailResponseDto getOwnerParkingLotDetail(long userId, long parkingLotId) throws BadRequestException;

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.18
     * 설명 : 이미지를 포함한 주차장 정보 수정
     * @param userId 사용자 id
     * @param parkingLotId 주차장 id
     * @param request 수정 값
     * @return void
     *  ---------------------
     * 2024.09.19 양건모 | 기능 구현
     * */
    void modifyOwnerParkingLotDetail(long userId, long parkingLotId, ParkingLotDto.ParkingLotModifyRequestDto request) throws IOException;
}
