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

    Optional<ParkingLot> findByParkingCenterId(String parkingCenterId);

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

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.07
     * 설명 : id 값이 일치하는 주차장에 대한 간략한 정보 제공
     * @param parkingLotId 주차장 id
     * @return 주차장명, 주소, 가격, 리뷰 개수
     *  ---------------------
     * 2024.09.07 양건모 | 기능 구현
     * 2024.09.07 양건모 | 가장 최신 리뷰를 전달하기 위해 서브쿼리 추가
     * */
    @Query("SELECT new org.gomgom.parkingplace.Dto.ParkingLotDto$ParkingLotPreviewResponseDto(" +
            "p.name, p.address, COALESCE(COUNT(r), 0), " +
            "(SELECT r2.review " +
            "FROM Review r2 " +
            "WHERE r2.parkingLot.id = p.id " +
            "ORDER BY r2.createdAt DESC" +
            " LIMIT 1)" +
            ") " +
            "FROM ParkingLot p " +
            "LEFT JOIN p.reviews r " +
            "WHERE p.id = :parkingLotId " +
            "GROUP BY p.id ")
    Optional<ParkingLotDto.ParkingLotPreviewResponseDto> getParkingLotPreviewById(Long parkingLotId);


    /*
    * @Author 김경민
    * @Date 2024.09.07
    *
    * 주차정보 가져오는 쿼리
    * */
    @Query("SELECT pl FROM ParkingLot pl WHERE pl.id = :parkingLotId")
    Optional<ParkingLot> getParkingLotReservationData(Long parkingLotId);
}
