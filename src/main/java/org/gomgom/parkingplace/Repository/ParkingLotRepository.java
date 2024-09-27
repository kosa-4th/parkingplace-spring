package org.gomgom.parkingplace.Repository;


import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    /**
     * @DATE 2024.09.23
     * <p>
     * 검색어를 통한 주차장 데이터 페이징
     */
    @Query("SELECT pl FROM ParkingLot pl WHERE " +
            "(:name IS NULL OR LOWER(pl.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:address IS NULL OR LOWER(pl.address) LIKE LOWER(CONCAT('%', :address, '%'))) " +
            "ORDER BY CASE WHEN pl.usable = 'Y' THEN 1 ELSE 0 END DESC, pl.updatedAt DESC ")
    Page<ParkingLot> findByNameAndAddress(
            @Param("name") String name,
            @Param("address") String address,
            Pageable pageable);


    /**
     * @Date 2024.09.23
     * Data가져오기
     */
    List<ParkingLot> findByUserId(Long userId);

    List<ParkingLot> findByUsable(Bool usable);

    @Query("select p.name from ParkingLot p where p.id = :parkingLotId")
    String findByParkingLotName(Long parkingLotId);

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.02
     * 설명 : 위도 경도 범위 내의 주차장들의 위도, 경도를 포함한 정보 제공
     * @param minLat, maxLat, minLon, maxLon 최소 위도, 최대 위도, 최소 경도, 최대 경도
     * @return List 형태로 id, 이름, 위도, 경도, 주소
     * ---------------------
     * 2024.09.05 양건모 | 기능 구현
     * 2024.09.09 양건모 | 본 서비스를 통해 예약 가능한 주차장인지 여부를 판단하기 위한 hasUser 컬럼 추가
     */
    @Query("SELECT new org.gomgom.parkingplace.Dto.ParkingLotDto$ParkingLotMarkerDto(" +
            "pl.id, pl.latitude, pl.longitude, pl.name, pl.address, " +
            "CASE WHEN pl.user IS NOT NULL THEN true ELSE false END" +
            ")" +
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
     * ---------------------
     * 2024.09.07 양건모 | 기능 구현
     * 2024.09.07 양건모 | 가장 최신 리뷰를 전달하기 위해 서브쿼리 추가
     * 2024.09.09 양건모 | 본 서비스를 통해 예약 가능한 주차장인지 여부를 판단하기 위한 hasUser 컬럼 추가
     */
    @Query("SELECT new org.gomgom.parkingplace.Dto.ParkingLotDto$ParkingLotPreviewResponseDto(" +
            "p.name, p.address, COALESCE(COUNT(r), 0), " +
            "(SELECT r2.review " +
            "FROM Review r2 " +
            "WHERE r2.parkingLot.id = p.id " +
            "ORDER BY r2.createdAt DESC " +
            "LIMIT 1), " +
            "CASE WHEN p.user IS NOT NULL THEN true ELSE false END" +
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

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.15
     * 설명 : 사용자 id로 등록된 주차장 조회
     * @param userId 사용자 id
     * @return 주차장 id, 주차장 이름 List 형태로 반환
     * ---------------------
     * 2024.09.15 양건모 | 기능 구현
     */
    @Query("SELECT new org.gomgom.parkingplace.Dto.ParkingLotDto$ParkingLotIdAndNameDto(" +
            "pl.id, pl.name " +
            ") " +
            "FROM ParkingLot pl " +
            "JOIN pl.user usr " +
            "WHERE usr.id = :userId")
    List<ParkingLotDto.ParkingLotIdAndNameDto> findIdAndNameByUserId(Long userId);

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.20
     * 설명 : userId, parkingLotId가 일치하는 주차장 Entity 조회
     * @param userId 사용자 id
     * @param parkingLotId 주치장 id
     * @return 주차장 상세 정보
     *  ---------------------
     * 2024.09.20 양건모 | 기능 구현
     * */
    @Query("SELECT DiSTINCT pl FROM ParkingLot pl " +
            "LEFT JOIN pl.parkingImages pi " +
            "LEFT JOIN FETCH pl.parkingSpaces ps " +
            "LEFT JOIN FETCH ps.carType ct " +
            "JOIN FETCH pl.user usr " +
            "WHERE pl.id = :parkingLotId " +
            "AND usr.id = :userId")
    Optional<ParkingLot> findByIdIncludeImageSpace(long userId, long parkingLotId);

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.23
     * 설명 : 검색한 위치 근처의 주차장 추천
     * @param longitude 검색한 장소의 경도
     * @param latitude 검색한 장소의 위도
     * @prarm maxDistance 최대 탐색 범위
     * @param startDateTime 예약 시작 시간
     * @param endDateTime 예약 종료 시간
     * @param carTypeId 차량 타입 id
     * @return 주차장 상세 정보
     *  ---------------------
     * 2024.09.23 양건모 | 기능 구현
     * 2024.09.26 양건모 | 쿼리 수정
     * */
    @Query(value = "WITH ranked_distances AS (\n" +
            "    SELECT \n" +
            "        pl.parking_lot_id, \n" +
            "        ps.parking_space_id, \n" +
            "        ROUND(ST_Distance_Sphere(point(:longitude, :latitude), POINT(pl.longitude, pl.latitude))) AS distance_m,\n" +
            "        PERCENT_RANK() OVER (ORDER BY ST_Distance_Sphere(point(:longitude, :latitude), POINT(pl.longitude, pl.latitude))) AS distance_percentile\n" +
            "    FROM tbl_parking_lot pl\n" +
            "    JOIN tbl_parking_space ps ON pl.parking_lot_id = ps.parking_lot_id\n" +
            "    WHERE ST_Distance_Sphere(point(:longitude, :latitude), POINT(pl.longitude, pl.latitude)) <= :maxDistance\n" +
            "      AND ps.car_type_id IN (1, :carTypeId)  -- 주차 공간의 car_type_id 조건 추가\n" +
            "),\n" +
            "ranked_prices AS (\n" +
            "    SELECT \n" +
            "        pl.parking_lot_id, \n" +
            "        ps.parking_space_id, \n" +
            "        MIN(CASE \n" +
            "                WHEN DAYOFWEEK('2024-09-24 14:30:00.000000') IN (1, 7) THEN \n" +
            "                    CASE \n" +
            "                        WHEN ps.weekend_all_day_price = 0 THEN \n" +
            "                            CEIL(TIMESTAMPDIFF(MINUTE, :startDateTime, :endDateTime) / 30.0) * ps.weekend_price\n" +
            "                        ELSE \n" +
            "                            LEAST(ps.weekend_all_day_price, CEIL(TIMESTAMPDIFF(MINUTE, :startDateTime, :endDateTime) / 30.0) * ps.weekend_price)\n" +
            "                    END\n" +
            "                ELSE \n" +
            "                    CASE \n" +
            "                        WHEN ps.week_all_day_price = 0 THEN \n" +
            "                            CEIL(TIMESTAMPDIFF(MINUTE, :startDateTime, :endDateTime) / 30.0) * ps.weekdays_price\n" +
            "                        ELSE \n" +
            "                            LEAST(ps.week_all_day_price, CEIL(TIMESTAMPDIFF(MINUTE, :startDateTime, :endDateTime) / 30.0) * ps.weekdays_price)\n" +
            "                    END\n" +
            "            END) AS price,\n" +
            "        PERCENT_RANK() OVER (ORDER BY MIN(CASE \n" +
            "                WHEN DAYOFWEEK(:startDateTime) IN (1, 7) THEN \n" +
            "                    CASE \n" +
            "                        WHEN ps.weekend_all_day_price = 0 THEN \n" +
            "                            CEIL(TIMESTAMPDIFF(MINUTE, :startDateTime, :endDateTime) / 30.0) * ps.weekend_price\n" +
            "                        ELSE \n" +
            "                            LEAST(ps.weekend_all_day_price, CEIL(TIMESTAMPDIFF(MINUTE, :startDateTime, :endDateTime) / 30.0) * ps.weekend_price)\n" +
            "                    END\n" +
            "                ELSE \n" +
            "                    CASE \n" +
            "                        WHEN ps.week_all_day_price = 0 THEN \n" +
            "                            CEIL(TIMESTAMPDIFF(MINUTE, :startDateTime, :endDateTime) / 30.0) * ps.weekdays_price\n" +
            "                        ELSE \n" +
            "                            LEAST(ps.week_all_day_price, CEIL(TIMESTAMPDIFF(MINUTE, :startDateTime, :endDateTime) / 30.0) * ps.weekdays_price)\n" +
            "                    END\n" +
            "            END)) AS price_percentile\n" +
            "    FROM tbl_parking_lot pl\n" +
            "    JOIN tbl_parking_space ps ON pl.parking_lot_id = ps.parking_lot_id\n" +
            "    WHERE ST_Distance_Sphere(point(:longitude, :latitude), POINT(pl.longitude, pl.latitude)) <= :maxDistance\n" +
            "      AND ps.car_type_id IN (1, :carTypeId)\n" +
            "    GROUP BY pl.parking_lot_id, ps.parking_space_id\n" +
            ")\n" +
            "SELECT \n" +
            "    pl.parking_lot_id, \n" +
            "    pl.name AS parking_lot_name, \n" +
            "    pl.address, \n" +
            "    ps.parking_space_id,\n" +
            "    rp.price, \n" +
            "    rd.distance_m, \n" +
            "    (0.5 * (1 - rd.distance_percentile)) + (0.5 * (1 - rp.price_percentile)) AS weighted_score\n" +
            "FROM tbl_parking_lot pl\n" +
            "JOIN tbl_parking_space ps ON pl.parking_lot_id = ps.parking_lot_id\n" +
            "JOIN ranked_distances rd ON rd.parking_lot_id = pl.parking_lot_id AND rd.parking_space_id = ps.parking_space_id\n" +
            "JOIN ranked_prices rp ON rp.parking_lot_id = pl.parking_lot_id AND rp.parking_space_id = ps.parking_space_id\n" +
            "ORDER BY weighted_score DESC", nativeQuery = true)
    List<Object[]> getRecommendedParkingLots(double longitude, double latitude, int maxDistance, LocalDateTime startDateTime, LocalDateTime endDateTime, long carTypeId);
}
