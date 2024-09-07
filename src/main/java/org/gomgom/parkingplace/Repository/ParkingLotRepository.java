package org.gomgom.parkingplace.Repository;


import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.List;


/**
 * @Author 김경민
 * @date 2024-09-04
 *
 */

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

    List<ParkingLot> findByUsable (Bool usable);

    @Query("select p.name from ParkingLot p where p.id = :parkingLotId")
    String findByParkingLotName (Long parkingLotId);
    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.02
     * 설명 : 위도 경도 범위 내의 주차장들의 위도, 경도를 포함한 정보 제공
     * @param minLat, maxLat, minLon, maxLon 최소 위도, 최대 위도, 최소 경도, 최대 경도
     * @return List 형태로 id, 이름, 위도, 경도, 주소
     *  ---------------------
     * 2024.09.05 양건모 | 기능 구현
     * */
    @Query("SELECT new org.gomgom.parkingplace.Dto.ParkingLotDto$ParkingLotMarkerDto(pl)" +
            "FROM ParkingLot pl " +
            "WHERE pl.latitude BETWEEN :minLat AND :maxLat " +
            "AND pl.longitude BETWEEN :minLon AND :maxLon")
    List<ParkingLotDto.ParkingLotMarkerDto> getParkingLots(
            @Param("minLat") double minLat,
            @Param("maxLat") double maxLat,
            @Param("minLon") double minLon,
            @Param("maxLon") double maxLon);
}
