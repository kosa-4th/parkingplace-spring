package org.gomgom.parkingplace.Service.parkingLot;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Repository.ParkingLotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;

    @Override
    public ParkingLotDto.ParkingLotMarkersDto getParkingLots(ParkingLotDto.ParkingLotListRequestDto request) {
        List<ParkingLotDto.ParkingLotMarkerDto> markers = parkingLotRepository.getParkingLots(request.getMinLat(), request.getMaxLat(), request.getMinLon(), request.getMaxLon());
        return new ParkingLotDto.ParkingLotMarkersDto(markers);
    }

    /*
        작성자: 오지수
         */
    @Override
    public ParkingLotDto.ParkingLotDetailResponseDto getParkingLotDetail(Long parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow();

        return new ParkingLotDto.ParkingLotDetailResponseDto(parkingLot);
    }
}
