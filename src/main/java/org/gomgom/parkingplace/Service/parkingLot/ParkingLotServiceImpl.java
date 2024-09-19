package org.gomgom.parkingplace.Service.parkingLot;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.gomgom.parkingplace.Entity.ParkingImage;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.ParkingSpace;
import org.gomgom.parkingplace.Entity.Review;
import org.gomgom.parkingplace.Repository.ParkingLotRepository;
import org.gomgom.parkingplace.Repository.ParkingSpaceRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.02
     * 설명 : 위도 경도 범위 내의 주차장들의 위도, 경도를 포함한 List 정보를 클래스로 한번 감싸 전달
     * @param request 최소 위도, 최대 위도, 최소 경도, 최대 경도
     * @return List 형태로 id, 이름, 위도, 경도, 주소
     *  ---------------------
     * 2024.09.05 양건모 | 기능 구현
     * */
    @Override
    public ParkingLotDto.ParkingLotMarkersResponseDto getParkingLots(ParkingLotDto.ParkingLotListRequestDto request) {
        List<ParkingLotDto.ParkingLotMarkerDto> markers = parkingLotRepository.getParkingLots(request.getMinLat(), request.getMaxLat(), request.getMinLon(), request.getMaxLon());
        return new ParkingLotDto.ParkingLotMarkersResponseDto(markers);
    }

    /**
     * 작성자: 오지수
     * 2024.09.05 : 주차장 상세 페이지에 전달할 정보
     * @param parkingLotId 주차장 id
     * @return
     */
    @Override
    public ParkingLotDto.ParkingLotDetailResponseDto getParkingLotDetail(Long parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow();

        return new ParkingLotDto.ParkingLotDetailResponseDto(parkingLot);
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.07
     * 설명 : 주차장의 간략한 정보 제공
     * @param parkingLotId 주차장 id
     * @return 주차장명, 주소, 가격, 리뷰 개수, 가장 최신 리뷰, 주차 구역 목록
     *  ---------------------
     * 2024.09.07 양건모 | 기능 구현
     * */
    @Override
    public ParkingLotDto.ParkingLotPreviewResponseDto getParkingLotPreview(Long parkingLotId) {
        ParkingLotDto.ParkingLotPreviewResponseDto preview = parkingLotRepository.getParkingLotPreviewById(parkingLotId)
                .orElseThrow();

        preview.setParkingSpaces(parkingSpaceRepository.getSpacesPreviewsByParkingLotId(parkingLotId));
        return preview;
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.15
     * 설명 : 사용자 id로 등록된 주차장 조회
     * @param userId 사용자 id
     * @return List 형태로 주차장 id, 주차장 이름
     *  ---------------------
     * 2024.09.15 양건모 | 기능 구현
     * */
    @Override
    public ParkingLotDto.MyParkingLotsReponseDto getMyParkingLots(Long userId) {
        List<ParkingLotDto.ParkingLotIdAndNameDto> list = parkingLotRepository.findIdAndNameByUserId(userId);
        return new ParkingLotDto.MyParkingLotsReponseDto(list);
    }

    @Override
    public ParkingLotDto.OwnerParkingLotDetailResponseDto getOwnerParkingLotDetail(long userId, long parkingLotId) throws BadRequestException {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow();

        if (parkingLot.getUser() == null || !parkingLot.getUser().getId().equals(userId)) {
            throw new BadRequestException();
        }

        //주차장 이미지 가공
        List<ParkingLotDto.MyParkingLotImage> images = new ArrayList<>();
        for (ParkingImage image: parkingLot.getParkingImages()) {
            images.add(new ParkingLotDto.MyParkingLotImage(image.getId(), image.getThumbnailPath()));
        }

        //주차구역 가공
        List<ParkingLotDto.MyParkingLotSpace> parkingSpaces = new ArrayList<>();
        for (ParkingSpace space: parkingLot.getParkingSpaces()) {
            parkingSpaces.add(new ParkingLotDto.MyParkingLotSpace(
                    space.getId(), space.getSpaceName(), space.getCarType().getCarTypeEnum().getKor(),
                    space.getWeekdaysPrice(), space.getWeekendPrice(), space.getWeekAllDayPrice(),
                    space.getWeekendAllDayPrice(), space.getWashPrice(), space.getMaintenancePrice(),
                    space.getAvailableSpaceNum()));
        }

        return new ParkingLotDto.OwnerParkingLotDetailResponseDto(
            parkingLot.getName(), parkingLot.getAddress(), parkingLot.getTel(), parkingLot.getWeekdaysOpenTime(),
                parkingLot.getWeekdaysCloseTime(), parkingLot.getWeekendOpenTime(), parkingLot.getWeekendCloseTime(), images, parkingSpaces
        );
    }

}
